# ✅ Todo List App (Fullstack: React + Spring Boot)

## 📌 프로젝트 개요

- **목표**: 프론트엔드와 백엔드를 연동한 Todo List 앱을 개발하고, SQA 기반 테스트 및 품질 관리 실습
- **기술 스택**:
  - Frontend: React (Vite + TypeScript)
  - Backend: Spring Boot 3.x (Java 17+)
  - DB: H2 (또는 MySQL)
  - API 통신: RESTful
  - 테스트: Jest, Cypress, JUnit, Swagger, Postman 등

---

## 🗂️ 프론트엔드 컴포넌트 구조

```
<TodoApp>
├── <TodoForm />         // 할 일 입력, 마감일 선택
├── <TodoFilter />       // 전체 / 완료 / 미완료 필터
├── <TodoList />         // 할 일 목록 출력
│    ├── <TodoItem />    // 각 할 일 항목 (텍스트, 체크박스, 삭제, 수정)
```

- **TodoApp**: 전역 상태 관리 (`todos`, `filter`)
- **TodoForm**: 항목 추가 입력, 마감일 선택
- **TodoFilter**: 현재 필터 상태 변경
- **TodoList**: 필터된 항목 목록 출력
- **TodoItem**: 개별 항목의 수정, 삭제, 체크 기능

---

## 🧾 Todo 상태 정의

```ts
interface Todo {
  id: number;
  content: string;
  completed: boolean;
  dueDate: string; // ISO format (예: 2025-04-15)
}

const [todos, setTodos] = useState<Todo[]>([]);
const [filter, setFilter] = useState<'all' | 'completed' | 'incompleted'>('all');
```

---

## ✅ 기능 정의 및 테스트 포인트

| 기능명 | 설명 | 테스트 포인트 |
|--------|------|----------------|
| 할 일 추가 | 입력 및 마감일 지정 → 추가 | 빈 입력 예외처리, DB 저장 |
| 할 일 삭제 | 항목 삭제 버튼 클릭 | 삭제 후 DB 반영 여부 확인 |
| 완료 체크 | 체크박스 클릭 → 상태 토글 | 완료 상태 변경, UI 확인 |
| 항목 수정 | 더블클릭 → 수정 모드 → 저장 | 빈 입력 예외처리, DB 반영 |
| 마감일 설정 | 날짜 입력 필드 추가 | 날짜 표시, 지난 날짜 강조 |
| 필터링 | 전체 / 완료 / 미완료 선택 | 리스트 필터링 정확성 확인 |

---

## 🌐 백엔드 API 명세

| 메서드 | 경로 | 설명 |
|--------|------|------|
| `GET` | `/api/todos` | 전체 목록 조회 |
| `POST` | `/api/todos` | 항목 추가 |
| `PUT` | `/api/todos/{id}` | 항목 수정 |
| `DELETE` | `/api/todos/{id}` | 항목 삭제 |

---

## 🧱 Spring Boot 백엔드 구성

### 📌 Entity

```java
@Entity
public class Todo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Boolean completed;
    private LocalDate dueDate;
}
```

### 📌 Repository

```java
public interface TodoRepository extends JpaRepository<Todo, Long> {}
```

### 📌 Controller

```java
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    @PutMapping("/{id}")
    public Todo update(@PathVariable Long id, @RequestBody Todo updated) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        todo.setContent(updated.getContent());
        todo.setCompleted(updated.getCompleted());
        todo.setDueDate(updated.getDueDate());
        return todoRepository.save(todo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}
```

---

## 🔧 CORS 설정

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173") // Vite dev 서버 주소
                .allowedMethods("*");
    }
}
```

---

## 🧪 통합 테스트 예시

- JUnit + MockMvc로 REST API 테스트
- 프론트: Cypress 또는 Playwright로 E2E 테스트
- Swagger 또는 Postman으로 API 수동 테스트

---

## 🧩 기타 확장 고려

| 항목 | 내용 |
|------|------|
| 예외 처리 | 빈 content → 400 오류, 존재하지 않는 id → 404 |
| 마감일 기준 정렬 | `GET /api/todos?sort=dueDate` 등 확장 |
| 인증 추가 | Spring Security + JWT로 인증 보호 가능 |
| DB 확장 | MySQL 또는 PostgreSQL 연동 가능 |

---

## ✅ 개발 순서

1. **Spring Boot 백엔드 구성**
   - Gradle 프로젝트 생성 (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `h2` 또는 `mysql-connector`)
   - Entity, Repository, Controller, CORS 설정
   - Swagger 적용 (선택)

2. **프론트엔드 UI 설계**
   - Vite + React + TypeScript 프로젝트 생성
   - `TodoApp`, `TodoForm`, `TodoList`, `TodoItem`, `TodoFilter` 컴포넌트 구현

3. **Axios를 통한 API 연동**
   - React 컴포넌트에서 `/api/todos`와 통신
   - GET/POST/PUT/DELETE 구현 및 상태 관리

4. **상태 저장/전환 로직 구현**
   - useState, useEffect 등으로 상태 관리
   - 완료 체크, 수정 모드, 마감일 기능 구현

5. **단위 테스트 & 통합 테스트 작성**
   - 프론트: Jest/React Testing Library, Cypress
   - 백엔드: JUnit + MockMvc

6. **정적 분석 및 품질 관리 도구 적용**
   - SonarQube, ESLint, Prettier 등 적용
   - 커버리지 측정, 코드 스멜 감지

7. **CI/CD 구성 및 배포 (선택)**
   - GitHub Actions, Docker, CloudType, Render, AWS 등 활용