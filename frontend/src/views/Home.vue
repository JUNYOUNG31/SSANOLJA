<template>
  <div class="home">
    <img src="../assets/logo.png" alt="logo" />
    <section class="test">
      <div v-on:click="GoogleLoginBtn">
        <button class="btn btn-lg btn-primary grey--text">
          <img src="../assets/googlelogo.png" alt="googlelogo" width="40" />Sign in with Google</button>
      </div>
      <div id="my-signin2" style="display: none"></div>
    </section>
  </div>
</template>

<script>
// import HelloWorld from '../components/HelloWorld'
import axios from "axios";
export default {
  name: "Home",
  data() {
    return {
      userData: {
        userEmail: null,
        userName: null,
      },
    };
  },
  methods: {
    GoogleLoginBtn: function () {
      var self = this;

      window.gapi.signin2.render("my-signin2", {
        scope: "profile email",
        width: 240,
        height: 50,
        longtitle: true,
        theme: "dark",
        onsuccess: this.GoogleLoginSuccess,
        onfailure: this.GoogleLoginFailure,
      });

      setTimeout(function () {
        if (!self.googleLoginCheck) {
          const auth = window.gapi.auth2.getAuthInstance();
          auth.isSignedIn.get();
          document.querySelector(".abcRioButton").click();
        }
      }, 1500);
    },
    async GoogleLoginSuccess(googleUser) {
      const googleEmail = googleUser.getBasicProfile().getEmail();
      if (googleEmail !== "undefined") {
        this.userData.userEmail = googleUser.getBasicProfile().getEmail();
        this.userData.userName = googleUser.getBasicProfile().getName();
      }
      this.insertUser();
    },
    //구글 로그인 콜백함수 (실패)
    GoogleLoginFailure(error) {
      console.log(error);
    },
    insertUser: function () {
      axios({
        method: "POST",
        url: "/api/login/insertUser",
        data: {
          userEmail: this.userData.userEmail,
        },
      })
        .then((res) => {
          if (res.data.status == "ok") {
            if (res.data.userNickname == null) {
              this.$router.push({
                name: "Lobby",
                params: {
                  userEmail: this.userData.userEmail,
                  userName: this.userData.userName,
                },
              });
            } else {
              this.$router.push({
                name: "Lobby",
                params: {
                  userEmail: res.data.userEmail,
                  userName: res.data.userNickname,
                },
              });
            }
          }
        })
        .catch((err) => console.log(err));
    },
  },
}; // components: {
//   HelloWorld,
// },
</script>

<style>
.home {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.home > img {
  width: 300px;
  margin-top: 100px;
  margin-bottom: 100px;
}
</style>