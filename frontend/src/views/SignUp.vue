<template>
  <div class="signup_container">
    <div >
      <img src="../assets/logo.png" alt="logo">
    </div>
    <div class="signup">
      <h1>Sign Up</h1> 
      <div>
        <label>아이디</label>
        <input v-model="userData.userId" class="form-control" type="text" id="userId" placeholder="Id" >
      </div>
      <div>
        <label>비밀번호</label>
        <input  v-model="userData.userPassword"  class="form-control" type="password"  id="userPassword" placeholder="Password" >
      </div>
      <div>
        <label>이메일</label>
        <input v-model="userData.userEmail" class="form-control" type="text" id="userEmail" placeholder="Email" >
      </div>
      <div>
        <label>닉네임</label>
        <input v-model="userData.userNickName" class="form-control" type="text" id="userNickName" placeholder="NickName">
      </div>
      <div class="signup-button-to-home">
        <button class="btn btn-lg btn-success">
          <router-link router-link :to="{ name: 'Home' }">가입하기</router-link>
        </button>
      </div>

    </div>  
    <br>
  </div>
</template>

<script>
  export default {
    name: 'Signup',    
    data () {
      return {
        userData: {
        userId : null,
        userPassword : null,
        userEmail : null,
        userNickName : null,
        }   
      }
    },
  }
</script>

<style>
.signup {
    background: rgba(255, 255, 255, 0.4);
    position: absolute;
    top: 10%;
    left: 30%;
    width: 40%;
    text-align: center;
    justify-content: center;
    padding: 5px;
    border-radius: 3rem;
    box-shadow: 0px 0px 10px 0px;
    padding-top: 3%;
    padding-bottom: 5%;
}
    h1 {
        cursor: default;
        user-select: none;
    }
    .signup_container img {
        width: 200px ;      
        margin-top: 50px;

    }
    .signup_container img:hover {
        box-shadow: 0px 0px 5px 0px;
        cursor: pointer;
    }

  .signup_container .signup h1{
  font-size: 32px;
  margin-bottom: 28px;
}
.signup_container .signup input{
  background: #333;
border-radius: 4px;
border: 0;
color: white;  
height: 50px;
line-height: 40px;
padding: 5px 20px;
margin-bottom: 20px;
width: 60%;
text-align: center;
margin-left: auto;
margin-right: auto;
}

.signup-button-to-home button a {
  color: white;
}
</style>