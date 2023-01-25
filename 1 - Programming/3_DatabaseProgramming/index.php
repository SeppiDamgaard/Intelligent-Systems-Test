<?php
    // Connect to the db
    $connString = "mysql:host=localhost;port=3306;dbname=intelligentsystems3;user=root;charset=utf8mb4";
    $pdo = new PDO($connString);

    // Insert into the db
    $statement = $pdo->prepare("INSERT INTO test(number, textstring, date) VALUES (45021, 'Hello Database!', '2013-12-31 23:59')");
    $statement->execute();

    // Get all entries from the db
    $statement = $pdo->prepare("SELECT * FROM test");
    $statement->execute();

    // Echo the entries
    $posts = $statement->fetchAll(PDO::FETCH_ASSOC);

    foreach ($posts as $post) {
        echo($post["number"].PHP_EOL);
        echo($post["textstring"].PHP_EOL);
        echo($post["date"].PHP_EOL);
    }
?>