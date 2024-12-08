console.log("読み込まれました")

document.querySelectorAll(".like-button").forEach(button => {
    button.addEventListener('click' , event => {
        fetch('/like' , {
            method: "POST",
        })
        .then(response => {
            if (response.ok) {
                alert("いいねしました")
            } else {
                alert("エラーが発生しました。")
            }
        });
    });
});