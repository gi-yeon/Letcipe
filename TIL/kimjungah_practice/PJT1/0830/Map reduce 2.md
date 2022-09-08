## Map reduce 2

![image-20220830163338549](C:\Users\jkiml\AppData\Roaming\Typora\typora-user-images\image-20220830163338549.png)

##### fork

인풋 파일은 미리 쪼개져서 분산 파일 시스템에 저장되어있다는 가정 하에, 사용자가 map과 reduce 함수를 정의한 프로그램을 실행시켜 프로세스를 실행하면 이는 마스터 노드, map을 실행할 워커 노드(Mapper노드), reduce를 실행할 워커 노드(Reducer 노드)들에 복사됨.

##### assign map and reduce

마스터 노드는 Mapper워커들에게 mapping 역할을, reducer 노드들에게는 reduce 역할을 수행하라고 지정해줌. 이 때, mapper 노드의 개수나 reducer 노드의 개수는 사용자가 설정할 수 있음.

##### read, map, local write

Mapper 노드들은 쪼개진 데이터 청크를 분산 파일 시스템으로부터 읽어옴. 그 다음 이 데이터에 Map 함수를 실행하여 K:V 형태의 Intermediate 데이터를 생성. 이를 자기 자신의 로컬 디스크에 저장. 그 다음 Mapper 노드는 마스터 노드에게 Mapping 작업을 모두 완료하였다고 알려줌.

##### reduce, write

마스터 노드는 Reducer 노드들에 reduce를 시작하라고 명령. reducer 노드들은 mapper 노드들의 디스크 공간에 저장되어 있는 Intermediate 데이터를 읽어옴. 그리고 reduce 함수를 실행해 최종 결과물을 산출한 후 파일 형태로 데이터를 출력

### 사전학습 과제의 wordcount 예제 분석

- Splitting, Mapping

  1. input 텍스트 파일을 쪼개 분산 파일 시스템에 저장
  2. 이렇게 쪼개진 텍스트 데이터는 3대의 Mapper노드에 각각 전달
  3. 그리고 각 노드들은 Map 함수를 실행하여 입력으로 들어온 텍스트 데이터를 파싱하여 Dear:1, Bear:1, River:1 과 같은 Intermediate Key Value를 생성합니다.

- **Shuffling**

  - 과정

    1. Mapping: map의 결과물로 intermediate key value 가 생성됨
    2. Partitioning: 각각의 키 값을 기준으로 어느 리듀서에서 처리 되어야 하는지 파티션을 결정
    3. Sorting: 같은 파티션 내의 값들은 키 값을 기준으로 소팅
    4. Combining: 파티션 내의 값들에 대해 미리 reduce 작업을 진행하여 데이터의 양을 줄임
    5. Spill to local disk: 처리된 값들을 Mapper 노드의 디스크 공간에 저장
    6. Notify Master: 매핑 과정을 완료 하였다는 알림과 파티셔닝 정보를 마스터 노드에게 알림
    7. Notify Reducers: 마스터 노드는 리듀서 노드들에게 이 정보를 전달
    8. Remote Read: 리듀서 노드들은 자신에게 할당된 파티션들만 매퍼들에게서 읽어와서 리듀스 작업을 준비.

  - Mapping으로 생성된 Intermediate Key Value 값을 Reducer 노드에 입력으로 전달하기 이전의 과정을 통틀어서 Shuffling이라고 부른다.

  - Shuffling이 필요한 이유?

    - ***동일한 키 값을 지닌 Intermediate Key Value Pairs는 동일한 Reducer에게 전달되어야 하며, 이 매핑 과정을 Shuffling이라고 한다.***

    ![image-20220830153720956](C:\Users\jkiml\AppData\Roaming\Typora\typora-user-images\image-20220830153720956.png)

    - MapReduce를 통해서 알고 싶은 것은 전체 텍스트에서 특정 단어가 얼마나 자주 등장했는지이다. 이를 위해 전체 텍스트 데이터를 3조각으로 나누고, 이를 Mapper 노드들에 분산 시켜 워드 카운팅을 진행. 문제는 이렇게 생성된 Intermediate Key Value들에서 노드는 서로 다르지만 같은 키 값을 지닌 쌍들이 있다는 점.

![image-20220830153734740](C:\Users\jkiml\AppData\Roaming\Typora\typora-user-images\image-20220830153734740.png)

*리듀서의 개수를 기준으로 키 값을 단순히 해쉬하게 되면 빈번하게 등장하는 단어 처럼 특정 reduce 작업의 부하가 높아질 우려가 있다고 함. 때문에 하둡은 직접 파티셔닝 로직을 작성할 수 있는 기능을 제공한다고 한다.*

map 함수를 거쳐서 생성된 intermediate key value pairs들은 가장 첫 째로 메모리 상의 버퍼에 저장됨. 이후 partitioning 과정을 거친다. 

### Partitioning?

- 해당 키가 처리되어야 하는 reduce task를 기준으로 데이터를 구분.
- 보통은 key 값을 해쉬 하여 파티션을 결정.
- 하둡에서는 기본적으로 키 값을 리듀서의 개수로 해쉬하는 방식을 사용한다고 한다. (ex) key mod R(num of reducers) 이렇게 해주면 각각의 키 값들이 어느 리듀서에서 처리되어야 하는 지를 구분할 수 있다. 각각의 파티션은 다시 키 값을 기준으로 소팅을 거쳐 Mapper 노드의 local disk 공간에 저장된다.



