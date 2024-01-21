# Refactoring

- [리팩터링 2판](https://product.kyobobook.co.kr/detail/S000001810241)
- [인프런 - 코드로 학습하는 리팩토링](https://inf.run/HJCN)

❗️ **코드 실행을 위한 사전 작업**

`home` 디렉토리에 `.github` 파일 생성 후 **repos** 권한을 가진 github personal access token 등록 필요

```yaml
oauth=GITHUB_PERSONAL_ACCESS_TOKEN
```

---

1. 이해하기 힘든 이름 (Mysterious Name)
    1. 함수 선언 변경하기 (Change Function Declaration)
        - 함수 이름
            - 함수의 구현 코드를 볼 필요 없이 호출문만 보고도 무슨 일을 하는지 파악할 수 있도록 작성
            - 함수의 기능을 설명하는 주석 작성 -> 주석 이름으로 함수 이름 만들어보기
        - 매개변수
            - 함수 내부의 문맥 결정
            - 의존성 결정 (활용 범위 결정 + 다른 모듈과의 결합도 결정)
    2. 변수 이름 바꾸기 (Rename Variable)
        - 사용 범위(scope)에 따라 중요도가 달라짐 (람다식에서 사용하는 변수 vs 함수의 매개변수)
    3.  필드 이름 바꾸기 (Rename Field)
        - `Record`의 필드 이름은 프로그램 전반에 걸쳐 참조되므로 매우 중요

2. 중복 코드 (Duplicated Code)
    1. 함수 추출하기 (Extract Function)
        - **의도**와 **구현** 분리하기
            - 의도 -> 이름을 통해 *어떻게가 아닌* **무엇을** 하는지 파악할 수 있도록 작성
            - 구현 -> 내부 코드로 확인하도록 작성
    2. 코드 정리하기 (Slide Statements)
        - 관련 있는 코드끼리 근처에 두기
    3. 메소드 올리기 (Pull Up Method)
        - 여러 하위 클래스에 동일한 코드가 있다면 **상위 클래스**로 올리기
        - 한쪽에서만 코드를 수정해 버그가 발생하는 경우 방지
       
3. 긴 함수 (Long Function)
    1. 임시 변수를 질의 함수로 바꾸기 (Replace Temp with Query)
        - 매개변수 감소
        - 코드 중복 감소
    2. 매개변수 객체 만들기 (Introduce Parameter Object)
        - 비슷한 매개변수가 계속해서 같이 넘겨질 때 사용
        - 데이터 사이의 관계가 명확해짐
        - 매개변수 수 감소
        - 모든 함수가 원소 참조 시 같은 이름 사용하므로 일관성 증가
    3. 객체 통째로 넘기기 (Preserve Whole Object)
        - 코드 변화에 대응 쉬움
        - 레코드에 담긴 데이터 중 일부를 받는 함수가 여러 개라면 그 함수들이 같은 데이터를 사용하는 부분이 있을 것이고, 그 부분의 로직이 중복될 가능성 높음
        - 똑같은 일부만 사용하는 코드가 많다면 => 따로 묶어서 클래스로 추출하라는 악취일 수도 (클래스 추출하기, Extract Class)
        - 이 리팩토링을 실행하지 않는 경우
          - 함수가 레코드에 의존하기를 원하지 않을 때
          - 레코드와 함수가 다른 모듈에 있을 때
    4. 함수를 명령으로 바꾸기 (Replace Function with Command)
        - 디자인 패턴 중 **커맨터 패턴**과 유사
        - 함수 분리를 한 후 위치가 잘못된 것 같거나, 향후 더 복잡해질거 같을 때 고려
        - 명령 객체 : 요청을 캡슐화한 객체
          - 대부분 **메소드 하나**로 구성되며, 이 메소드를 요청해 실행하는 것이 이 객체의 목적
          - 되돌리기(undo)같은 보조 연산 제공 가능
          - 수명주기 정밀하게 제어 가능
          - 상속, 훅(hook)으로 사용자 맞춤 가능
          - 일급 함수(first-class function)를 지원하지 않는 언어에서 일급 함수 기능 흉내 가능
    5. 조건문 분해하기 (Decompose Conditional)
        - **조건**과 **액션** 모두 **의도**를 표현해야함
        - 코드 분해 후 각 코드 덩어리를 의도를 살린 이름의 함수 호출로 변경
        - 1. 해당 **조건**이 무엇인지 강조하고, 2. **무엇**을 분기했는지, 3. **왜** 분기했는지 명확하게 변경
    6. 반복문 쪼개기 (Split Loop)
        - 서로 다른 일이 한 함수에서 일어나고 있다는 신호일 수도
        - 리팩토링과 최적화 구분하기
    7. 조건문을 다형성으로 바꾸기 (Replace Conditional with Polymorphism)
        - 기본 동작은 슈퍼 클래스로, 각 case는 서브 클래스로

4. 긴 매개변수 목록 (Long Parameter List)
    - 함수의 매개변수가 많을수록 함수의 역할을 이해하기 어려움
    1. 매개변수를 질의 함수로 바꾸기 (Replace Parameter with Query)
        - 한 매개변수로 다른 매개변수를 알아낼 수 있다면 이는 중복 매개변수
        - 가능하면 함수를 호출하는 쪽의 책임을 줄이고 **함수 내부에서 책임**지도록 함
            - 매개변수에 값을 전달하는 것은 함수를 호출하는 쪽의 책임
        - 리팩토링 시 의존성이 생길 수 있음에 유의
    2. 플래그 인수 제거하기 (Remove Flag Argument)
        - 함수 내부 로직 분기를 위해 플래그 인수 사용
        - 플래그가 많이 있으면 너무 많은 일을 한 곳에서 한다는 의미
        - 플래그가 하나라면 메소드가 하는 일의 의미를 파악하기 힘들다는 의미
            ```
            bookConcert(customer, false), bookConcert(customer, true)
            vs
            bookConcert(customer), premiumBookConcert(customer)
            ```
        - 특정 기능 하나만 수행하는 명시적인 함수로 변경하기
        - 플래그 인수 대신 **조건문 분해하기(Decompose Condition)** 활용하기
    3. 여러 함수를 클래스로 묶기 (Combine Functions into Class)
        - 비슷한 매개변수를 여러 함수에서 사용한다면 해당 메소드를 모아서 **클래스**로 만들기
            - 함수들이 공유하는 공통 환경 명확하게 표현 가능
            - 인수를 줄여서 간결한 함수 호출 가능
            - 시스템의 다른 부분에 전달하기 위한 참조 제공 가능
        - 클래스 내부로 **메소드**를 옮기고, 데이터를 **필드**로 만들면 매개변수 줄일 수 있음

5. 전역 데이터 (Global Data)
    - 어디서나 접근 및 변경 가능
    1. 변수 캡슐화 하기 (Encapsulate Variables)
        - 메소드는 점진적으로 변경할 수 있으나, 데이터는 한 번에 모두 변경해야됨
          - ex) 기존 메소드 남겨두고 새로 리팩토링한 메소드 사용하는 방식...
        - => 변수에 직접 접근하기보다 메소드를 이용하면 변경에 유연하게 대처 가능
        - 검증이나 추가 로직 적용이 쉬움
        - 불변 데이터는 굳이 캡슐화할 필요 없음
    
6. 가변 데이터 (Mutable Data)
    1. 변수 쪼개기 (Split Variable)
        - 재할당되는 변수 == 여러 용도로 사용하는 변수
        - **변수 하나 당 하나의 책임(Responsibility)를 지도록**
        - 상수 활용하기 (final)
        - 참고) 재할당되어도 적절한 경우
            - 반복문 순회에 사용되는 인덱스
            - 값을 축적시키기 위해 사용하는 변수
    2. 질의 함수와 변경 함수 분리하기 (Separate Query from Modifier)
        - **명령-조회 분리 (command-query separation) 규칙**
            - 어떤 값을 리턴하는 함수는 사이드 이팩트가 없어야 한다.
        - 사이드 이팩트 없이 값을 조회할 수 있어야 메소트를 테스트하고 변경하기 쉬움
    3. 세터 제거하기 (Remove Setting Method)
        - 세터를 제공한다 == 해당 필드가 **변경될 수 있다**
        - 변경이 필요없는 필드라면 **생성자를 이용**하고 **세터 제거**하기
    4. 파생 변수를 질의 함수로 바꾸기 (Replace Derived Variable with Query)
        - 변경될 수 있는 변수를 줄이는 방법
        - 계산해서 알아낼 수 있는 변수는 제거하기
        - 계산에 필요한 데이터가 불변이라면 결과값 역시 불변이므로 유지해도 괜찮음
    5. 여러 함수를 변환 함수로 묶기 (Combine Functions into Transform)
        - 관련있는 여러 파생 변수를 만들어내는 함수가 여러 곳에 있고 사용된다면 => 파생 변수를 변환 함수(transform function)을 통해 한 곳에 모으기
        - 변환 함수 : 원본 데이터를 입력받아서 필요한 정보를 모두 도출한 뒤, 각각을 출력 데이터의 필드에 넣어 반환하는 함수
        - 소스 데이터가 변경될 수 있는 경우에는 `여러 함수를 클래스로 묶기` 사용
        - 소스 데이터가 변경되지 않는 경우에는 변환 함수를 사용해 불변 데이터 필드로 만들고 재사용
    6. 참조를 값으로 바꾸기 (Change Reference to Value)
        - 레퍼런스 객체 vs 값 객체
        - 값 객체는 객체가 가진 필드의 값으로 동일성 확인
        - 값 객체는 불변
        - 변경 내용을 다른 곳으로 전파하려면 레퍼런스 객체를, 아니라면 값 객체 사용
    
7. 뒤엉킨 변경 (Divergent Change)
    - 단일 책임 원칙(SRP, Singel Responsibility Principle)을 지키지 않아 발생
    - 응집도는 높이고, 결합도는 낮추기
    - 책임 분리를 통해 서로 다른 문제는 서로 다른 모듈에서 해결
    1. 단계 쪼개기 (Split Phase)
        - 서로 다른 일을 하는 코드는 각기 다른 모듈로 분리
            - ex) 전처리 -> 주요 작업 -> 후처리
        - 서로 다른 데이터를 사용하는 것을 기반으로 단계 분리 고민하기
        - 중간 데이터(Intermediate Data)를 만들어 단계를 구분하고 매개변수를 줄일 수 있음
    2. 함수 옮기기 (Move Function)
        - 응집도가 높다면 찾고, 이해하고, 변경하기 쉬움
        - 해당 함수가 다른 문맥(클래스)에 있는 데이터(필드)를 더 많이 참조하는 경우
        - 해당 함수를 다른 클라이언트(클래스)에서도 필요로 하는 경우
    3. 클래스 추출하기 (Extract Class)
        - 하위 클래스를 만들어 책임 분산
        - 데이터나 메소드 중 일부가 매우 밀접한 관련이 있는 경우
        - 일부 데이터가 대부분 같이 변경되는 경우
        - 데이터 또는 메소드 증 일부를 삭제한다면 어떻게 될 것인가?
    
8. 산탄총 수술 (Shotgun Surgery)
    - 뒤엉킨 변경의 반대 상황 : 한 변경 사항으로 인해 여러 모듈을 수정해야 하는 상황
    - 불필요한 결합도가 높거나, 응집도가 낮아서 발생
    1. 필드 옮기기 (Move Field)
        - 어떤 데이터를 항상 어떤 레코드와 함께 전달하는 경우
        - 어떤 레코드를 변경할 때 다른 레코드에 있는 필드를 변경해야 하는 경우
        - 여러 레코드의 동일한 필드를 수정해야 하는 경우
    2. 함수 인라인 (Inline Function)
        - <> 함수 추출하기
        - 함수 이름보다 본문을 봤을 때 더 직관적으로 이해할 수 있는 경우
        - 리팩토링이 잘못된 경우
        - 단순히 메소드 호출을 감싸는 우회형 메소드인 경우
        - 상속 구조에서 오버라이딩하고 있는 메소드는 인라인 불가
    3. 클래스 인라인 (Inline Class)
        - <> 클래스 추출하기
        - 클래스의 책임을 옮기다보니 클래스 존재 이유가 빈약해지는 경우
    
9. 기능 편애 (Feature Envy)
    - 어떤 모듈에 있는 함수가 다른 모듈에 있는 데이터나 함수를 더 많이 참조할 때 발생
    - 함수를 적절한 위치로 옮기는 것이 필요
        - 함수 옮기기
        - 함수의 일부분만 다른 곳에서 많이 참조한다면 함수 추출하기 
        - 여러 모듈을 참조한다면 가장 많은 데이터를 참조하는 곳으로 옮기거나 함수를 여러 개로 쪼개서 각 모듈로 분산
    - 데이터와 해당 데이터를 참조하는 행동을 같은 곳에 둬야함
        - 예외적으로 데이터와 행동을 분리한 패턴(전략 패턴, 방문자 패턴) 적용할 수도 

39. 데이터 뭉치

40. 기본형 집착
    1. 기본형을 객체로 바꾸기
    42. 타입 코드를 서브 클래스로 바꾸기
    43. 조건부 로직을 다형성으로 바꾸기
    
44. 반복되는 switch문

45. 반복문
    1. 반복문을 파이프라인으로 바꾸기
    
47. 성의없는 요소
    1. 계층 합치기
    
49. 추측성 일반화
    1. 죽은 코드 제거하기
    
51. 임시 필드
    1. 특이 케이스 추가하기
    
53. 메시지 체인
    1. 위임 숨기기
    
55. 중재자
    1. 중재자 제거하기
    57. 슈퍼클래스를 위임으로 바꾸기
    58. 서브클래스를 위임으로 바꾸기
    
59. 내부자 거래

60. 거대한 클래스
    1. 슈퍼클래스 추출하기
    
62. 서로 다른 인터페이스의 대안 클래스들

63. 데이터 클래스
    1. 레코드 캡슐화하기
    
65. 상속 포기

66. 주석
    1. 어셔션 추가하기
    
68. 리팩토링 카탈로그
    1. 기본 기술
    70. 캡슐화
    71. 기능 옮기기
    72. 데이터 조직화
    73. 조건부 로직 간소화
    74. API 리팩토링
    75. 상속 다루기
