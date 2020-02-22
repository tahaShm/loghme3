<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Food Party</title>
    <style>
        img {
            width: 50px;
            height: 50px;
        }
        li {
            display: flex;
            flex-direction: row;
            padding: 0 0 5px;
        }
        div, form {
            padding: 0 5px
        }
        .old-price {
            text-decoration: line-through;
        }
    </style>
</head>
<body>
<ul>

    <li>menu:
        <ul>
            <li>
                <img src="url of food image" alt="logo">
                <div>restaurant 1</div>
                <div>food1</div>
                <div>legendary food!</div>
                <div class="old-price">20000 Toman</div>
                <div>19000 Toman</div>
                <div>remaining count:2</div>
                <div>popularity: 0.4</div>
                <form action="" method="POST">
                    <!-- TODO: Add extra inputs to pass restaurant and food ids!  -->
                    <button type="submit">addToCart</button>
                </form>
            </li>
        </ul>
    </li>
    <li>menu:
        <ul>
            <li>
                <img src="url of food image" alt="logo">
                <div>restaurant 2</div>
                <div>food2</div>
                <div>legendary food 2!</div>
                <div class="old-price">30000 Toman</div>
                <div>29000 Toman</div>
                <div>remaining count:5</div>
                <div>popularity: 0.8</div>
                <form action="" method="POST">
                    <!-- TODO: Add extra inputs to pass restaurant and food ids!  -->
                    <button type="submit">addToCart</button>
                </form>
            </li>
        </ul>
    </li>
</ul>
</body>
</html>
