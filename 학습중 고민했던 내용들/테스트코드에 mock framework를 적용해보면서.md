### mockito를 적용하게 된 이유

테스트 코드에 대해 찾아보고 학습하던 도중 
테스트코드는 의존성이 적으면 적을 수록 좋다. 
조금 더 자세히 말하면 '단위 테스트 코드'는 
의존성이 적으면 적을 수록 좋다 라는 말을 보게 되었다.

어떻게하면 의존성을 줄일 수 있을까 고민하던 중 mockito framework라는 것을 알게 되었고
mockito를 적용해보면서 느낀점을 적어보려 한다.

### mockito 적용하기

---
일단 mockito는 spring-boot-starter-test를 적용하면 알아서 의존성을 추가해준다.(spring boot 짱짱맨)

```gradle
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

이제 mockito를 적용하기 전에 작성되었던 간단한 테스트 코드를 먼저 보여주겠다.

```java

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class)
class BoardServiceTest {

    @Autowired
    BoardService boardService;

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
        Board findBoard = boardService.findOne(boardId);
        assertThat(findBoard.getTitle()).isEqualTo("test board title");
        assertThat(findBoard.getContent()).isEqualTo("test board content");
        assertThat(findBoard.getWriter()).isEqualTo("test board writer");
        assertThat(findBoard.getViewCount()).isEqualTo(1);
    }

    @Test
    void 게시글_수정() throws Exception {
        //given
        Long boardId = boardService.register(board);

        Board findBoard = boardService.findOne(boardId);
        findBoard.modifiedBoard("test board modified title", "test board modified content");

        Board findModifiedBoard = boardService.findOne(boardId);

        assertThat(findModifiedBoard.getTitle()).isEqualTo("test board modified title");
        assertThat(findModifiedBoard.getContent()).isEqualTo("test board modified content");
    }
}
```

처음 테스트코드를 공부하기 시작하고 나름 만족하면서(?) 적었던 초보적인 수준의 테스트 코드지만
'단위테스트 코드는 의존성을 줄이면 줄일수록 좋다.' 라는 말을 보고나서 불만을 갖게 되었다.

1. 단위테스트는 빠르고 가볍게 실행되야되는데 boardRepository를 호출해서 DB와 직접 통신한다. 
2.  테스트코드 하나하나 실행할때마다 스프링컨테이너가 실행는 시간이 포함되기 때문에 빠르게 실행 할 수 없다.
---

이 외의 단점이 있을 수 있지만 지금 내가 갖고있던 불만은 이정도 이다. 
(이 불만도 다른 해결법이 있을 수 있고, 어쩌면 이 불만이 잘못된 불만일 수 도 있다.)

---
Mockito를 적용한 뒤의 테스트 코드를 적어보겠다.

```java

package com.slcube.jpaboard.service;

import com.slcube.jpaboard.domain.Board;
import com.slcube.jpaboard.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @InjectMocks
    BoardService boardService;

    @Mock
    BoardRepository boardRepository;

    Board board;

    @BeforeEach
    public void given() throws Exception {
        this.board = Board.builder()
                .title("test board title")
                .content("test board content")
                .writer("test board writer")
                .build();
        Long boardId = 1L;
        doAnswer(invocation -> boardId)
                .when(boardRepository).save(board);
        doAnswer(invocation -> board)
                .when(boardRepository).findOne(boardId);
    }

    @Test
    void 게시글_작성() throws Exception {
        //when
        Long boardId = boardService.register(board);

        //then
        Board findBoard = boardService.findOne(boardId);
        assertThat(findBoard.getTitle()).isEqualTo("test board title");
        assertThat(findBoard.getContent()).isEqualTo("test board content");
        assertThat(findBoard.getWriter()).isEqualTo("test board writer");
        assertThat(findBoard.getViewCount()).isEqualTo(1);
    }

    @Test
    void 게시글_수정() throws Exception {
        //given
        Long boardId = boardService.register(board);

        Board findBoard = boardService.findOne(boardId);
        findBoard.modifiedBoard("test board modified title", "test board modified content");

        Board findModifiedBoard = boardService.findOne(boardId);

        assertThat(findModifiedBoard.getTitle()).isEqualTo("test board modified title");
        assertThat(findModifiedBoard.getContent()).isEqualTo("test board modified content");
    }
}
```

새로운 어노테이션과 새로운 코드가 보인다.

@Mock Annotation은 가짜의 객체를 생성해주고 @InjectionMocks Annotation은 가짜로 생성된 객체들을 주입해주는 annotation으로 이해하고있다.  

그리고 given method에 doAnswer method가 보이는데 지정한 method를 실행하면 지정한 output이 나오게끔 세팅 해주는 코드이다.  

실행해보면 스프링이 실행안되면서 아주 빠르고 가볍게 테스트가 통과되는 모습을 볼 수 있었다.

---
### mockito를 적용하면서 생긴 의문점
의존성을 최대한 제거해 빠르고 가볍게 실행할 수 있는 테스트코드가 되었지만 몇가지 찜찜한 구석이 생겼다.

1. 지정한 실행하면 지정한 output이 나오게끔 개발자가 임의로 적용했는데 이것에 대한 불안감이 생겼다.  
누가 실수로 잘못된 데이터를 지정하면 그 테스트코드는 과연 올바른 테스트 코드인가.  
2. 결국 개발자가 기대하는 값을 지정하는데 개발자가 제어할 수 없는 외부의 api의 output이 변경되면 그 기대하는 값도 변경해줘야 한다. 
이 부분에서 불안정한 테스트코드 라는 생각이 들었다.
3. mockito와 같은 framework를 사용하지않고 의존성을 줄일 수 있는 방법은 어떤것이 있을까?
---

이번에 mockito를 간단하게 적용해보면서 굉장히 많은 생각을 했다.  
단순히 돌아가는 코드가 아닌 조금 더 견고한 코드를 작성하고 싶은데, 견고한 코드는 테스트코드가 무조건 필요하다 생각한다.  
언제 어디서든 테스트 할 수 있는 테스트 코드, 반복해서 실행하기에 부담스럽지 않은 테스트코드  
그런 테스트코드를 위해 mocking framework를 이용해 의존성을 줄였지만 그로인해 생긴 또다른 의문...  

아직 부족함을 많이 느낀다.
