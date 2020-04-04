val datasource by extra {
    mapOf(
        "default" to DataSource(
            url = "jdbc:mysql://localhost:33060",
            user = "root",
            password = "",
            driver = "com.mysql.cj.jdbc.Driver"
        ),
        "test" to DataSource(
            url = "jdbc:mysql://localhost:33061",
            user = "root",
            password = "",
            driver = "com.mysql.cj.jdbc.Driver"
        )
    )
}
