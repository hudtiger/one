<template>
  <div>
    <div class="left">
       <dl>
           <dt>{{detail.caption}}</dt>
           <dd><flow-template v-if="detail.subStep" v-bind:detail="detail.subStep"></flow-template></dd>
           <dd class="leftP"><flow-template v-if="detail.nextStep" v-bind:detail="detail.nextStep"></flow-template></dd>
       </dl>
      </div>
  </div>
</template>
<script>
export default {
  name: 'flow-template',
  props: ['detail'],
 }
</script>
<style>
  .left{
    margin-left: 100px;
  }
  .leftP{
    margin-left: -100px;
  }
</style>
