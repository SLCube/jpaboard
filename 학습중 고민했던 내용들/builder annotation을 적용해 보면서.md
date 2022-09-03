--- 
일하기 시작하고 가장 해결하고 싶었고, 고민했던 내용 중 하나가 Setter method였다.  

'외부에서의 접근을 막겠다'는 의도로 보통 class의 field는 private 접근제어자를 이용해 선언한다.  

여기서 의문점이 생겼다.
1. 외부에서의 접근을 막겠다면서 Setter method를 모든 필드에 열면 그건 private한 필드가 아니지 않나?
2. Setter method가 없다면.. 값의 변경이 필요할때 어떻게 변경 해줘야되지?

이 의문점을 해결하지 못한채 JPA 학습을 시작했다.

아래는 간단한 게시글 Entity Class이다. (아직 JPA 학습이 부족하다. 부족한 내용이 있지만 일단 넘어가자.)

```java
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;
    private String writer;
    private int viewCount;
    private String deleteYn;
}
```

Entity Class를 만들고 적은 Test Code이다.

```java
@Test
void 게시글_등록(){
    Board board = new Board();
    board.setTitle("test board title");
    board.setContent("test board content");
    /* 중략.. */
    
    Long boardId = boardRepository.save(board);

    Board findBoard = boardRepository.findOne(boardId);
    
    assertThat(findBoard).isEqualsTo(board);
}   
```

--- 

나는 Setter method를 일단 없애고 싶었기에   
Setter Annotation을 지우고 모든 필드를 인자로 받는 생성자를 만든 후 테스트 코드를 수정했다.

```java
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsContructor
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;
    private String writer;
    private int viewCount;
    private String deleteYn;
}
```

```java
@Test
void 게시글_등록(){
    Board board = new Board("test board title", "test board content", "test board writer", 0, "N");
    
    Long boardId = boardRepository.save(board);

    Board findBoard = boardRepository.findOne(boardId);
    
    assertThat(findBoard).isEqualsTo(board);
}   
```
코드가 더 이상해졌다.

생성자의 인자값이 무엇을 의미하는지 파악하는 것이 Setter method를 사용할 때 보다 더 어려워 졌다.  
추가로 조회수(viewCount), 삭제여부(deleteYn)과 같은 필드들은 초기값이 0과 "N"이 들어가기로 했는데,  
객체를 생성할 때마다 생성자의 인자값으로 넣어 주는 것은 비효율적이다.  
당장 고칠 수 있는 코드를 고쳐보자
---

```java
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;
    private String writer;
    private int viewCount;
    private String deleteYn;
    
    public Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
```

```java
@Test
void 게시글_등록(){
    Board board = new Board("test board title", "test board content", "test board writer");
    
    Long boardId = boardRepository.save(board);

    Board findBoard = boardRepository.findOne(boardId);
    
    assertThat(findBoard).isEqualsTo(board);
}   
```

코드가 1%정도 나아진 것 같다.  
하지만 생성자의 각 인자가 어떤 필드에 해당하는 값인지 아직 파악하기 힘든 코드이다.

---

코드를 위의 상태로 둔지 1주일정도 지났을 때 디자인 패턴에 대해 좀 찾아봤었다.  
이미 몇몇 디자인 패턴은 알고있는 상태였다.(제대로 알고있나? 라고 물음을 던진다면 어렴풋이 개념정도를 알고있는 수준이라 답하겠다.)  

Singleton, Adapter, Factory method, Template method등등....  

디자인패턴에 대해 검색하던 중 Builder Pattern를 찾게 되었고 내가 원하던 코드를, 내 고민을 풀어줄 코드를 찾게 되었다.

```java
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;
    private String writer;
    private int viewCount;
    private String deleteYn;
    
    @Builder
    public Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
```

```java
@Test
void 게시글_등록(){
    Board board = Board.builder()
        .title("test board title")
        .content("test board content")
        .writer("test board writer")
        .build();
    
    Long boardId = boardRepository.save(board);

    Board findBoard = boardRepository.findOne(boardId);
    
    assertThat(findBoard).isEqualsTo(board);
}   
```

---
Lombok의 Builder Annotation을 이용했다.  
(GoF의 디자인패턴에서 말하는 Builder Pattern과 Lombok Builder Annotation은 차이가 있다 들었다. 이 내용은 추후에 학습할 예정이다.)  

미래의 내가 보면 아주 초보적인 코드지만, 저 코드를 작성했을 때 굉장히 만족스러웠다.  

코드 한줄한줄이 어떤 의미를 갖는지 다른곳에 있는 코드를 찾지 않아도 알수있는 '깔끔한' 코드가 완성됐다 느꼈기 때문이다.(여기서 깔끔하다 라고 말하는 것은 전과 비교했을 때 경우다. 더 좋은 방법이 있을 수 있다.)  

---
그렇게 만족하고 있던 중 한가지 의문점이 생겼다.

    어.. 생성자를 public으로 열어뒀는데 Builder Annotation을 사용하고도 생성자를 이용해 객체를 생성할 수 있나...?(지금 생각해보니 굉장히 멍청한 생각이다.)

결론은 당연히 된다.  
생성자가 갖고있는 단점, 각 인자가 어떤 필드를 의미하는지 의미전달이 어렵다를 해결하려 Builder Annotation을 이용했는데  
내가아닌 누군가가 생성자를 이용해 객체를 생성하면 어떻게 하지??  

생성자를 private 접근제어자를 이용해 접근을 막아줘보자

```java
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;
    private String writer;
    private int viewCount;
    private String deleteYn;
    
    @Builder
    private Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
```

테스트 코드는 같으니 생략하겠다.

---

마지막으로 Entity Class에 Setter method를 삭제하면서 풀린 의문점들을 적어보겠다.

1. 모든 필드에 대해 Setter method를 열어두면 private 접근제어자로 선언하는것이 의미가 있는가?/
2. 생성자가 필요한 인자가 많아질수록 각 인자가 의미하는바를 한번에 파악하기 어려운데 어떻게 해결할 수 있는가.

추가로 Java를 처음 공부할 때 생성자를 private 접근제어자를 사용해 선언하는것에 대해 의문을 가졌었는데  
이 부분에 대한 의문점도 어느정도 해소 되었다.  
(꼭 이러한 케이스에만 private 생성자가 필요한 것은 아니다. Singleton Pattern에서도 사용된다. 이 밖에도 많이 있겠지만 아직 두가지 케이스말고는 생각나는 케이스가 없다.)