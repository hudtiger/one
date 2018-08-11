<template>
<div>
   <flow-template v-model="flowData" v-bind:detail="flowData"></flow-template>
</div>
</template>
<script>
import flowTemplate from './flowTemplate';
import Vue from "vue";
Vue.component('flow-template',require("./flowTemplate"))

export default {
  name: 'item',
  data() {
    return {
      flowData:{
          caption:"Step1",
          operator:[],
          state:"START",
          nextStep:{
              caption:"Step2",
              operator:[],
              state:"START",
              nextStep:{caption:"Step3",operator:[],state:"START"},
              subStep:{
                caption:"Step2-SubStart",
                operator:["Zhou","Jhon","Steven"],
                state:"START",
                nextStep:{
                  caption:"Step2-SubNext1",
                  operator:["Jerry"],
                  state:"START",
                  nextStep:{caption:"Step2-SubNext2",operator:["Tomy"],state:"START"},
                  }, 
                }
            }, 
            subStep:{
                caption:"Step1-SubStart1",
                operator:["Micheal","Chen"],
                state:"START",
                nextStep:{caption:"Step1-subNext2",operator:["Zhou"],state:"START"},
            }
        },
    }
  },
  component:{
    //"flow-template":flowTemplate
  }
}
</script>
