# osu!4J
The osu API wrapper written in Java

***

### Example

```java
OsuClient client = new OsuClient("CLIENT_ID", "CLIENT_SECRET");
try {
        User user = client.getUser("Gary50613");
        System.out.println(user.name);
} catch (IOException | InvalidTokenException | NotFoundException e) {
    e.printStackTrace();
}
```

### Install
replace `VERSION` with current version [![release](https://img.shields.io/github/v/release/Gary50613/osu4j?color=dark_green&include_prereleases)](https://github.com/Gary50613/osu4j/releases/latest)

```xml
<dependency>
    <groupId>tw.kane</groupId>
    <artifactId>osu4j</artifactId>
    <version>VERSION</version>
</dependency>
```

### Support
- [Discord](https://discord.gg/ct2ufag) [![Discord](https://img.shields.io/discord/600363644991176822.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/ct2ufag)
