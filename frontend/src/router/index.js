import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Room from '../views/Room.vue'
import Lobby from '../views/Lobby.vue'
import SignUp from '../views/SignUp.vue'
import Spyfall from '../components/games/Spyfall.vue'
import Fakeartist from '../components/games/Fakeartist.vue'
import Telestation from '../components/games/Telestation.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },  
  {
    path: '/room',
    name: 'Room',
    component: Room,
    children : [
      {
        path: '/spyfall',
        name: 'Spyfall',
        component: Spyfall
      },
      {
        path: '/takeartist',
        name: 'Fakeartist',
        component: Fakeartist
      },
      {
        path: '/telestation',
        name: 'Telestation',
        component: Telestation
      }
    ]
  },
  { 
    path: '/lobby',
    name: 'Lobby',
    component: Lobby
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: SignUp
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
