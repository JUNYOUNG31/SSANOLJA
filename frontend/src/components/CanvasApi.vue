<template>
<div id="gamearea">
                <div class="top">
                  <div  class="paper-input left back-image" style="background-color : white; padding : 10px">
                  <canvas ref="canvas" id="js-paint" 
                  @mousemove="onMouseMove"
                  @mousedown.left="startPainting"
                  @mouseup ="stopPainting"
                  @mouseleave="stopPainting"
                  @click.left="onMouseClick"
                  @contextmenu="handleCM"
                  width="700"
                  height="500"  ></canvas>
                  </div>
                    <div class="controls right">
                      <button @click="startFilling" class="v-btn" :disabled="drawComplete">
                        <img src="../assets/paint/roller.png" style="width:100%; height : 100%;"/>
                      </button>
                      <button @click="stopFilling" :disabled="drawComplete">
                          <img src = "../assets/paint/pencil.png"  style="width:100%; height : 100%;"/>
                      </button>
                      <button @click="startErasing" :disabled="drawComplete">
                        <img src="../assets/paint/eraser.png"  style="width:100%; height : 100%;"/>
                      </button>
                      <button @click="resetAll" :disabled="drawComplete">
                        <img src="../assets/paint/easel.png"  style="width:100%; height : 100%;"/> 
                      </button>
                      <button @click="canvasComplete()" :disabled="drawComplete">
                        <img src="../assets/paint/checkbox.png"  style="width:100%; height : 100%;"/> 
                      </button>
                    </div>
                </div>
                <div class="bottom">
                  <div class="colors left"><!-- 색깔 정렬-->
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(0, 0, 0)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(255,255,255)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(255,59,48)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(255,149,0)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(255,204,0)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(76,217,100)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(90,200,250)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(0,122,255)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(88,86,214)"></div>
                  </div>
                  <div class = "left inputarea" style="display:flex">
                    <p style="display:inline-block; margin:20px 15px;">o</p>
                    <input type="range" value="5.0" min="0.1" max="100.0" style="margin:7px 0;"  @change="onRangeChange">
                    <p style="display:inline-block; margin:12px 7px; font-size:25px">O</p>
                  </div>
                </div>
                
  </div>
</template>


<script>

export default{
  

data:function(){
  return{
    canvas : document.getElementById("js-paint"),
    painting : false,
    filling : false,
    lineWidth :   2.5,
    strokeStyle : '#000000',
    colors : document.getElementsByClassName("color"),
    drawComplete : false,
  }
},
  methods:{
    // drawingCompleted() {
    //   this.myComplete = !this.myComplete
    // },
    canvasComplete() {
     if(this.drawComplete == false){
        this.drawComplete = true;
      }
        this.$emit("canvasCompleted");
    },
    saveClick(){//그림 저장하는 코드
      this.drawComplete = false;
      this.image = this.$refs.canvas.toDataURL("image/jpeg");
      this.$emit("setDrawData", this.image)
      console.log(this.image);
    },
    handleCM(event){
      event.preventDefault();
    },
    onRangeChange(event){
      this.value = event.target.value;
      this.ctx.lineWidth = this.value;
    },
    colorChange(event){ 
      this.style = event.target.style;
      this.ctx.strokeStyle = this.style.backgroundColor;
    },
    onMouseMove(event){
        let x = event.offsetX;
        let y = event.offsetY;
        if(this.drawComplete==true){
          this.painting = false;
        }else{
        if(!this.filling){
          if(!this.painting){
                this.ctx.beginPath();
                this.ctx.moveTo(x,y);
          }else{
            this.ctx.lineTo(x,y);
            this.ctx.stroke();
          }
        }
        }
      },
      onMouseClick(){
        if(this.drawComplete==true){
          this.filling = false;
        }else if(this.filling){
              console.log(this.$refs.canvas.width)
              this.ctx.closePath();
              this.ctx.beginPath();
              this.ctx.fillStyle = this.ctx.strokeStyle;
              this.ctx.fillRect(0, 0, this.$refs.canvas.width, this.$refs.canvas.height);
          }
      },
      stopDrawing(){
        this.stopFilling();
        this.stopPainting();
      },
      startPainting(){
        this.painting = true;
        console.log("페인팅 + "+this.painting)
      },
      stopPainting(){
        this.painting = false;
      },
      stopFilling(){
        this.stopErasing();
        this.filling = false;
              console.log("스탑필링 + "+this.filling)
      },
      startFilling(){
        this.filling = true;
        this.stopErasing();
        console.log("스타트필링 + "+this.filling)
      },
      stopErasing(){
        this.ctx.globalCompositeOperation = 'source-over';
      },
      startErasing(){
        this.ctx.globalCompositeOperation = 'destination-out';
        this.filling = false;
      },
      resetAll(){
        this.ctx.clearRect(0,0,this.$refs.canvas.width, this.$refs.canvas.height);
        this.ctx.fillStyle='white';
        this.ctx.fillRect(0,0,this.$refs.canvas.width, this.$refs.canvas.height);
      },

    },
    mounted(){
        this.ctx = this.$refs.canvas.getContext('2d');
        this.ctx.fillStyle='white';
        this.ctx.fillRect(0,0,this.$refs.canvas.width, this.$refs.canvas.height);
        this.ctx.lineCap = 'round';
        console.log("dkkdkdkdk"+ this.$refs.canvas.width, this.$refs.canvas.height )
    }

}



</script>
<style scoped>

.top .bottom{
  display: flex;
}



    .left{
      float : left;
    }
    
    .right{
      float : right;
    }

.controls{
      background-color: rgb(216, 215, 215);
      border-radius: 15px;
      margin: 40px 15px;

}

.controls > button{
    display : block;
    margin : 10px;
    width:77px;
    height : 77px;

    box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
    min-width: 50px;
    border-radius: 5px;
    border: none;
    font-size: 14px;
    cursor: pointer;
    margin-bottom : 5px;

    padding : 0;
}

canvas {
  /* /* box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
  border-radius: 30px;/* */
  background-color: white;
}

.color {
  height: 35px;
  width: 35px;
  margin: 4px 2px;
  cursor: pointer;
  box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
  border-radius: 25px;

}

.colors {
  margin-left: 10px;
  margin-top: 10px;
  display: flex;
}


button:active {
  transform: scale(0.95);
}

button:focus {
  outline: none;
}

</style>