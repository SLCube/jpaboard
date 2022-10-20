## 테스트코드에 Mockito 제거해보기
---
오늘 날짜는 2022-09-29 목요일이다. 저번에 적은 글(찾아보니 2022-09-03에 작성했더라)과 이어지고 싶어서 날짜부터 적었다.   
- 이전 글 : [테스트 코드에 Mockito를 적용해보면서](https://github.com/SLCube/jpaboard/blob/main/%ED%95%99%EC%8A%B5%EC%A4%91%20%EA%B3%A0%EB%AF%BC%ED%96%88%EB%8D%98%20%EB%82%B4%EC%9A%A9%EB%93%A4/%ED%85%8C%EC%8A%A4%ED%8A%B8%EC%BD%94%EB%93%9C%EC%97%90%20mock%20framework%EB%A5%BC%20%EC%A0%81%EC%9A%A9%ED%95%B4%EB%B3%B4%EB%A9%B4%EC%84%9C.md)

이전에 Mockito를 적용해보면서 주입받는 Bean의 method의 결과를 코드상에서 정해준다는 것에 거부감을 가진다고 적었다. 순수한 테스트코드가 아니라 생각했었다.  
약 한달쯤 생각을 해봤지만 Mockito를 생각없이 사용하는거엔 거부감을 느꼈다. (우리회사는 그런 개발문화는 없...)  
그래서 이번엔 간단한 게시판CRUD 테스트 코드에 적용된 Mockito를 제거해봤다.  

첫번째로 단순히 class로 구현된 BoardRepository를 interface로 바꾸고 기존 BoardRepository를 interface를 상속받는 BoardRepositoryImpl로 바꿨다.

```java
package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;

import java.util.List;

public interface BoardRepository {
    public Long save(Board board);
    public Board findOne(Long boardId) throws Exception;
    public List<Board> findAll();
    public Long remove(Long boardId) throws Exception;
}


```

```java
package com.slcube.jpaboard.repository;

import com.slcube.jpaboard.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final EntityManager em;

    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    public Board findOne(Long boardId) throws Exception {
        return em.createQuery("select b from Board b where b.id = :boardId and b.deleteYn = 'N'", Board.class)
                .setParameter("boardId", boardId)
                .getSingleResult();
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b where b.deleteYn = 'N'", Board.class)
                .getResultList();
    }

    public Long remove(Long boardId) throws Exception {
        Board findBoard = findOne(boardId);
        return findBoard.deleteBoard();
    }
}
```

우리의 BoardRepositoryImpl은 JPA를 통해 외부자원(DB)에 의존하고있다. DB에 의존하는걸 제거하기 위해 FakeBoardRepository를 만들었다.

```java
package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.repository.board.BoardRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FakeBoardRepository implements BoardRepository {
    private Map<Long, Board> boardLinkedMap = new LinkedHashMap<>();

    @Override
    public Long save(Board board) {
        boardLinkedMap.put(board.getId(), board);
        return board.getId();
    }

    @Override
    public Board findOne(Long boardId) throws Exception {
        return boardLinkedMap.get(boardId);
    }

    @Override
    public List<Board> findAll() {
        List<Board> boardList = new ArrayList<>();
        for (Board board : boardLinkedMap.values()) {
            if (board.getDeleteYn().equals("N")) {
                boardList.add(board);
            }
        }
        return boardList;
    }

    @Override
    public Long remove(Long boardId) throws Exception {
        boardLinkedMap.remove(boardId);
        return boardId;
    }
}
```

순수 자바코드로 테스트코드를 작성해보고 싶었기 때문에 게시글 저장은 LinkedHashMap을 이용했다.(HashMap은 순서를 보장하지 않는다.) 간단한 CRUD이기 때문에 가짜 repository를 작성하는건 어렵지 않았다.

```java
package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class BoardServiceTest {
    BoardService boardService = new BoardService(new FakeBoardRepository());

    Board board;

    @BeforeEach
    public void given() throws Exception {
        this.board = Board.builder()
                .title("test board title")
                .content("test board content")
                .writer("test board writer")
                .build();
    }

    @Test
    void 게시글_작성() throws Exception {
        //when
        Long boardId = boardService.register(board);

        //then
        Board findBoard = boardService.findBoard(boardId);
        assertThat(findBoard.getTitle()).isEqualTo("test board title");
        assertThat(findBoard.getContent()).isEqualTo("test board content");
        assertThat(findBoard.getWriter()).isEqualTo("test board writer");
        assertThat(findBoard.getViewCount()).isEqualTo(1);
    }

    @Test
    void 게시글_수정() throws Exception {
        //given
        Long boardId = boardService.register(board);

        Board findBoard = boardService.findBoard(boardId);
        findBoard.modifiedBoard("test board modified title", "test board modified content");

        Board findModifiedBoard = boardService.findBoard(boardId);

        assertThat(findModifiedBoard.getTitle()).isEqualTo("test board modified title");
        assertThat(findModifiedBoard.getContent()).isEqualTo("test board modified content");
    }
}
```
순수 자바로 작성한(?) 테스트 코드이다. 이전과 차이점은 Mocking된 객체가 어떤 행동을 하는지 명시해준 부분을 지웠다. 하단에 실제 테스트 method는 바꾼것이 없다.

---
### mockito를 제거하면서 느낀점
일단 테스트코드에 대한 이해가 많이 필요하다는걸 다시 느꼈다. 지금 작성하고 있는 느낀점이 올바르게 느낀것이 맞는가 라는 의문이 있기 때문이다. 맞게 느꼈건 틀리게 느꼈건 내가 지금 이 순간에 느끼고 있는것을 적는건 의미있는 행동이기 때문에 적고있다.  

<br>
이전에 mockito를 적용했을땐 repository를 mocking하고 repository의 method가 어떤 행동을 할지 내가 명시했었다. 그리고 특정 method가 어떤 행동을 하는지 명시하는것이 거부감이 들었고 순수한 테스트코드라는 느낌은 안들었다.  

이번엔 LinkedHashMap이라는 간단한 InMemoryDB를 만들어 가짜 Repository를 만들었고, DB와 통신하지않지만 Service입장에선 정말 DB에 데이터를 넣는것처럼 구현해봤다.(조금 더 깔끔한 테스트 코드를 만들고 싶지만 내가 뭘 잘못하고있는지 모르고있는게 큰듯하다.) Service입장에서 DB에 데이터를 넣는것 처럼 동작하게 만들었기 때문에 Repository의 method가 어떤 행동을 하는지 명시할 필요가 없어졌고, 테스트코드를 통해 순수하게 이 객체가 무슨일을 하는지 보여줄수 있게 됐다. 

### mockito를 제거하면서 생긴 의문점
Test Code에서 Spring을 제거하고 Mockito를 제거한 뒤 순수하게 Java코드만으로 테스트코드를 작성하면서 순수한 테스트코드란 이런것이구나!! 라는 걸 아주아주 간단하게나마 느꼈다. 그와 반대로 생긴 의문점이 있다. 바로 가짜 객체를 직접 작성했다는 점이다.
내가 작성한 코드는 아주 간단하게 때문에 투자시간이 굉장히 짧았지만, 아주 복잡한 비즈니스 로직이 담긴 객체라면..? 어떠할까 라는 상상을 해봤다.  
<br>

누군가가 테스트코드는 테스트일뿐인데 시간을 많이 들여서 작성할 필요가 있는가 라는 질문을 했을 때 나는 1도 고민없이 당연히 작성해야된다 라고 말할것이다.  
하지만 mockito를 이용하면 되는걸 굳이 가짜객체를 새로 작성할 필요가 있는가 라는 질문을 하게 된다면 아직은 고민을 많이 할거같다.  
다음 목표는 이 질문에 답할 수 있도록 공부하는것으로 잡아야겠다.  
일하면서 테스트코드를 작성하려고 노력은하지만 회사에 그런 개발문화가 없어서 계속 흐지부지되고 있다. 