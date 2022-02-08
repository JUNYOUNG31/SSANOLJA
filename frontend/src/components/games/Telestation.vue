<template>
  <div class="telestation-container">
    <h1>텔레스테이션</h1>
    <p>
			<label>키워드</label>
			<input v-model="keyword" class="form-control" type="text" required placeholder="키워드를 입력하세요">
      <!-- <input v-model="userData.userName" class="form-control" type="text" required placeholder="닉네임을 입력하세요"> -->
		</p>
    <p class="text-center">
						<button class="btn btn-lg btn-success" @click="postTest">입력 완료</button>
		</p>
    <p @click="inputKeyword">테스트</p>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'Telestation',
  props:{
    streamManager: Object,
  },
  data () {
		return {
			keyword: '',
		}
	},
  methods: {
    inputKeyword: function () {
    axios({
					method:'GET',
					url: '/api/telestations/inputKeyword'
			})
			.then(res => {
        console.log(res)
        console.log(res.data)
				this.keyword = res.data
        console.log(this.keyword)
			})
			.catch(err => console.log(err))
    },
    postTest: function () {
    axios({
				method:'POST',
				url: '/api/telestations/postTest',
         data: { 
           keyword: this.keyword
         }
			})
			.catch(err => console.log(err))
    },
  },
}
</script>

<style>
.telestation-container {
  height: 100%;
}
</style>