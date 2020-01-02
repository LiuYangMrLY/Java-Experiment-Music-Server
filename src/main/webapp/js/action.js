
function loadAndPlay(src) {
	
}

function setLike(e) {
    e.src = "./pic/like.png";
    e.setAttribute("onclick", "setDisLike(this)");
}

function setDisLike(e) {
    e.src="./pic/dislike.png";
    e.setAttribute("onclick", "setLike(this)");
}

function setPic(e) {
    // let target = e.target;
    // console.log(e);
    alert(e);
    e.src="./pic/dislike.png";

    // if (target.getAttribute('src').match("./pic/like.png"))
    //     target.src = "./pic/dislike.png";
    // else target.src = "./pic/like.png";
}
function setSongListPage() {
    window.location.href = "http://localhost:8080/soundwave.html";
}
function setLikePage() {
    window.location.href = "http://localhost:8080/likeList.html";
}
function setCreateListPage() {
    window.location.href = "http://localhost:8080/createList.html";
}
function setRankPage() {
    window.location.href = "http://localhost:8080/rankList.html";
}