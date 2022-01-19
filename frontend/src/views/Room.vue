<template>
    <v-container><!--세로배열-->
      <v-row class="row-cols-5 div1" style="border: hidden"> <!--div1 로고 & 화면4개--><!--가로배열-->
        <v-col class="div1-1"><img src="../assets/logo.png" alt="logo"></v-col> <!--div1-1 로고-->
        <v-col>화면1</v-col>
        <v-col>화면2</v-col>
        <v-col>화면3</v-col>
        <v-col>화면4</v-col>
      </v-row> 
      <v-row class="div2" ><!--div2--><!--가로배열-->
        <div class="div2-1"><!--div2-1 화면 4개--><!--세로배열-->
          <div>화면5</div>
          <div>화면6</div>
          <div>화면7</div>
          <div>화면8</div>
        </div> 
        <v-col class="div2-2"> <!--div2-2 게임화면--><!--세로배열-->
          <v-row class="div3"><!--div3 게임선택 및 레디 --><!--가로배열-->
            <v-row class="div3-1 row-cols-3"> <!--div3-1 게임선택--><!--가로배열-->
              <v-col>스파이폴</v-col>
              <v-col>가에뉴다</v-col>
              <v-col>텔레스테이션</v-col>
            </v-row> 
            <div class="div3-2"> <!--div3-2 레디 --><!--세로배열-->
              <v-col>초대코드</v-col>
              <v-col>레디</v-col>
              <v-col>스타트</v-col>
            </div>
          </v-row>
          <div class="div4">게임설명</div><!--div4 게임설명-->
        </v-col> 
      </v-row>
    </v-container>
</template>


<script>
export default {
  name: "Room",
 // data: function () {
 //  return { 
 //   players : [
 //    {id : "player1"},
 //    {id : "player2"},
 //    {id : "player3"},
 //    {id : "player4"},     
 //   ]
 // }
 // }
}

</script>
<style>
div {
  height: auto;
  border: 1px solid black;  
}
/* 메인 화면 */
.container {
    height: 860px;
}
/* column 패딩 0 주기 */
.container .col {
  padding: 0;
}
/* row 패딩 마진 0 주기 */
.container .row {
padding: 0;
margin: 0;
}

/* 로고 있는 칸 ( 가운데 정렬)*/
.container .div1-1 {
  box-sizing: border-box;
  text-align: center;
  border: hidden;
}
/* 로고 */
.container .div1-1 img {
  height: 100%;
}
/* 화면 1,2,3,4 */
.container .div1 .col {
padding: 5px;
/* margin: 0; */
height: 160px;
}
/* 화면 5,6,7,8 + 게임화면 */
.container .div2 {
  border: hidden; 
}
/* 왼쪽 사이드 화면 비율 20% */
.container .div2-1 {
  border: hidden; 
  width: 20%;
}
/* 화면 5,6,7,8 */
.container .div2-1 div {
  /* border: hidden;  */
  height: 160px;
  padding: 5px;
}
/* 게임 화면 */
.container .div2-2  {
  border: hidden; 
  margin-left: 5px;
}

.container .div3 {
  border: hidden; 
}
.container .div3-1 {
  border: hidden; 
  height: 120px;
}
/* 게임준비쪽  */
.container .div3-2  {
  border: hidden; 
  width: 150px;
}
.container .div3-2 > div {
  width: 150px;
  height: 40px;
}
.container .div4 {
  height: 520px;
}


@media (min-width: 1904px) {
  .container {
  height: 935px;
  }
  .container .div4 {
  height: 570px;
  }
  .container .div1 .col {
  padding: 5px;
  height: 180px;
  }
  .container .div2-1 div {
  height: 180px;
  padding: 5px;
  }
.container .div3-1 {
  border: hidden; 
  height: 150px;
  }
  .container .div3-2  {
    border: hidden; 
    width: 200px;
  }
  .container .div3-2 > div {
  width: 200px;
  height: 50px;
  }
}

</style>