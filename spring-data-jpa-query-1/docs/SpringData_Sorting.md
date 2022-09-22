## 1. 概述

在本教程中，我们将学习如何**使用Spring Data对查询结果进行排序**。

首先，我们将看一下我们想要查询和排序的数据表。然后我们将讨论如何使用Spring Data来实现这一点。

## 2. 测试数据

下面我们有一些测试数据。虽然我们在这里将它表示为一个表，但我们可以使用Spring Data支持的任何一种数据库来持久化它。

我们要回答的问题是，“谁占据了飞机的哪个座位？”为了使这更加用户友好，我们将按座位号排序。

| First Name  | Last Name  | Seat Number |
|:-----------:|:----------:|:-----------:|
|    Jill     |   Smith    |     50      |
|     Eve     |  Jackson   |     94      |
|    Fred     |   Bloggs   |     22      |
|    Ricki    |   Bobbie   |     36      |
|    Siya     |   Kolisi   |     85      |

## 3. 实体

要创建Spring Data Repository，我们需要提供一个实体类以及一个id类型。

在这里，我们将乘客Passenger建模为JPA实体，但我们也可以轻松地将其建模为MongoDB文档或任何其他模型抽象：

```java

@Entity
class Passenger {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(nullable = false)
    private String lastName;

    @Basic(optional = false)
    @Column(nullable = false)
    private int seatNumber;
    // constructor, getters ...
}
```

## 4. Spring Data使用排序

我们可以有几种不同的方式来使用Spring Data进行排序。

### 4.1 使用OrderBy方法关键字排序

一种选择是使用Spring Data的派生方法，从而根据方法名和签名生成查询。

这里要对数据进行排序，只需在方法名中包含关键字OrderBy，以及我们想要排序的属性名称和顺序(Asc或Desc)。

我们可以使用这个约定来创建一个查询，按座位号升序返回我们的乘客：

```java
interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByOrderBySeatNumberAsc();
}
```

我们还可以将此关键字与所有标准Spring Data方法名称结合使用。

让我们看一个按lastName查找乘客并按座位号排序的方法示例：

```java
interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByLastNameOrderBySeatNumberAsc(String lastName);
}
```

### 4.2 使用Sort参数

我们的第二种方法是包含一个Sort参数，指定要排序的属性名称和顺序：

```text
List<Passenger> passengers = repository.findAll(Sort.by(Sort.Direction.ASC, "seatNumber"));
```

在这种情况下，我们使用findAll()方法，并在调用它时传递Sort参数。

我们还可以将此参数添加到新的方法定义中：

```java
interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByLastName(String lastName, Sort sort);
}
```

最后，如果我们使用分页，我们可以在Pageable对象中指定Sort：

```text
Page<Passenger> page = repository.findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "seatNumber")));
```

## 5. 总结

我们有两种使用Spring Data对数据进行排序的简单方法：使用OrderBy关键字的派生方法，或使用Sort对象作为方法参数。