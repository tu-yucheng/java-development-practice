## 1. 概述

在本教程中，我们将阐明Spring中的HttpMessageNotWritableException：“No converter found for return value of type”异常。

首先，我们将解释异常的主要原因。然后，我们将深入研究如何使用真实示例重现它，最后如何修复它。

## 2. 原因

通常，当Spring无法获取返回对象的属性时，会发生此异常。

**此异常的最典型原因通常是返回的对象没有任何用于其属性的公共getter方法**。

默认情况下，Spring Boot依赖于Jackson库来完成序列化/反序列化请求和响应对象的所有繁重工作。

因此，**该异常的另一个常见原因可能是缺少或使用了错误的Jackson依赖项**。

简而言之，检查此类异常的一般准则是检查是否存在：

+ 默认构造函数
+ Getters
+ Jackson依赖

请记住，异常类型已从java.lang.IllegalArgumentException更改为org.springframework.http.converter.HttpMessageNotWritableException。

## 3. 实例

现在，让我们看一个产生org.springframework.http.converter.HttpMessageNotWritableException的示例。

为了演示一个真实的用例，我们将使用Spring Boot构建一个用于学生管理的基本REST API。

首先，**让我们创建我们的模型类Student，并假装忘记生成getter方法**：

```java
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String grade;

    public Student() {
    }

    public Student(int id, String firstName, String lastName, String grade) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
    }

    // setters ...
}
```

其次，我们创建一个带有单个处理程序方法的Spring控制器，通过其id检索Student对象：

```java

@RestController
@RequestMapping(value = "/api")
public class StudentRestController {

    @GetMapping("/student/{id}")
    public ResponseEntity<Student> get(@PathVariable("id") int id) {
        // Custom logic
        return ResponseEntity.ok(new Student(id, "John", "Wiliams", "AA"));
    }
}
```

现在，如果我们使用CURL向 http://localhost:8080/api/student/1发送请求：

```shell
curl http://localhost:8080/api/student/1
```

将返回以下响应：

```json
{
    "timestamp": "2022-09-18T09:29:18.781+00:00",
    "status": 406,
    "error": "Not Acceptable",
    "path": "/api/student/1"
}
```

查看日志，Spring抛出了HttpMessageNotWritableException：

```text
[org.springframework.http.converter.HttpMessageNotWritableException: No converter found for return value of type: class cn.tuyucheng.taketoday.boot.noconverterfound.model.Student]
```

最后，让我们创建一个测试用例，看看当Student类中没有定义getter方法时Spring的行为：

```java

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentRestController.class)
class NoConverterFoundIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Disabled("Remove Getters from Student class to successfully run this test case")
    void whenGettersNotDefined_thenThrowException() throws Exception {
        String url = "/api/student/1";

        this.mockMvc.perform(get(url))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(HttpMessageNotWritableException.class))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage())
                        .contains("No converter found for return value of type"));
    }
}
```

## 4. 解决方案

**防止该异常的最常见解决方案之一是为我们想要以JSON返回的每个对象的属性定义一个getter方法**。

因此，让我们在Student类中添加getter方法，并创建一个新的测试用例来验证一切是否按预期工作：

```java
class NoConverterFoundIntegrationTest {

    @Test
    void whenGettersAreDefined_thenReturnObject() throws Exception {
        String url = "/api/student/2";

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }
}
```

一个不明智的解决方案是将模型类的属性定义为public，这不是100%安全的方法，因为它违反了几个最佳实践。

## 5. 总结

在这篇简短的文章中，我们解释了导致Spring抛出org.springframework.http.converter.HttpMessageNotWritableException：
“No converter found for return value of type”的原因。

然后，我们讨论了如何重现该异常以及如何在实践中解决它。