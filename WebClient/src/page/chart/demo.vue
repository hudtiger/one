<template>
  <div>
    <div class="cl">
        <el-table :id="asideId" :class="asideClass" :data="tableData" stripe>
        <el-table-column prop="task" label="项目" width="100"></el-table-column>
        <el-table-column prop="start" label="起始日期" width="100"></el-table-column>
        <el-table-column prop="days" label="时长" width="100">
            <template slot-scope="scope">
                <el-button type="text" size="small" @click="handleEdit(scope.row)">{{scope.row.days}}</el-button>
            </template>
        </el-table-column>
        <el-table-column prop="end" label="终止日期"></el-table-column>
        </el-table>
        <div :class="chartClass" :id="chartId" :style="{height:height,width:width}" ref="myEchart"></div>
    </div>
    <el-dialog title="项目进度" :visible.sync="dialogTableVisible" :before-close="handleClose">
        <el-form :model="editItem">
            <el-form-item label="起始日期" :label-width="formLabelWidth">
                <el-date-picker v-model="editItem.start" type="date" placeholder="选择日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
            <el-form-item label="时长" :label-width="formLabelWidth">
                <el-input v-model="editItem.days" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="终止日期" :label-width="formLabelWidth">
                <el-date-picker v-model="editItem.end" type="date" placeholder="选择日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="closeDialog(0)">Cancel</el-button>
            <el-button type="primary" @click="closeDialog(1)">Confirm</el-button>
        </span>
    </el-dialog>
    <div :class="stepClass" :id="stepId">
        <el-steps :active="active" finish-status="success">
            <el-step title="步骤 1"></el-step>
            <el-step title="步骤 2"></el-step>
            <el-step title="步骤 3"></el-step>
        </el-steps>
        <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
    </div>
  </div>
</template>
<script>
import echarts from 'echarts'
//基准时间换算
const getDays=(date,initDate)=>{
        initDate = initDate||"2016-05-01";
        let dateDefault = Date.parse(initDate)
        let dateIn = Date.parse(date)
        return (dateIn-dateDefault)/(24*60*60*1000)
    }
//浅拷贝
const shallowCopy=(src)=> {
    return syncData(src,null)
}
//数据同步
const syncData=(src,target)=> {
  var dst = target||{};
  for (var prop in src) {
    if (src.hasOwnProperty(prop)) {
      dst[prop] = src[prop];
    }
  }
  return dst;
}

export default {
  props: {
    chartClass: {type: String, default: 'chart'},
    chartId: {type: String, default: 'divChart'},
    asideId:{ type:String, default:"divAside"},
    asideClass:{type:String, default:"aside"},
    stepId:{ type:String, default:"divStep"},
    stepClass:{type:String, default:"step"},
    width: {type: String, default: '700px'},
    height: {type: String, default: '450px'
    }
  },
  data() {
    return {
      dialogTableVisible:false,
      chart: null,
      tableData: [
          { task: 'task1', start: '2016-05-02', days: '2', end: '2016-05-04'},
          { task: 'task2', start: '2016-05-04', days: '2', end: '2016-05-06'},
          { task: 'task3', start: '2016-05-04', days: '3', end: '2016-05-07'},
          { task: 'task4', start: '2016-05-09', days: '4', end: '2016-05-13'},
          { task: 'task5', start: '2016-05-13', days: '2', end: '2016-05-15'},
          { task: 'task6', start: '2016-05-14', days: '2', end: '2016-05-16'},
          { task: 'task7', start: '2016-05-02', days: '14', end: '2016-05-16'}
        ],
      currentItem:{},//记录当前对象
      editItem:{},
      formLabelWidth: '120px',
      active: 0
    }
  },
  mounted() {
    this.initChart();
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose();
    this.chart = null;
  },
  computed:{
    getCurrentItem:function(){
        console.log("computed property:object clone???")
        return shallowCopy(this.currentItem);
    } 
  },
  methods: {
    next() {
        if (this.active++ > 2) this.active = 0;
    },
    handleClose:function(nextHandle){
        console.log("before close")
        nextHandle()
    },
    closeDialog:function(flag){
        if(flag){//确认保存，同步修改数据
            syncData(this.editItem,this.currentItem)
            this.initChart()
        }
        this.dialogTableVisible = false
    },
    handleEdit:function(row){
        //遍历数组改变editeFlag
        this.dialogTableVisible = true
        this.currentItem = row  //指向当前编辑对象
        this.editItem = shallowCopy(row) //编辑当前对象副本
        console.log(this.editItem)
    },
      
    initChart() {
        let yData = []
        let sTime = []
        let tTime = []
        let eTime = []
        console.log("chart refresh")
        this.tableData.forEach(element => {
            yData.unshift(element.task)
            sTime.unshift(getDays(element.start))
         //   tTime.unshift(element.days)            
            tTime.unshift({value:element.days,name:element.start})
            eTime.unshift(getDays(element.end))
        })
      this.chart = echarts.init(this.$refs.myEchart);
      // 把配置和数据放这里
      this.chart.setOption({
        color: ['#ff7f50','#3cb371','#b8860b','#87cefa','#da70d6','#32cd32','#6495ed',
               '#ff69b4','#ba55d3','#cd5c5c','#ffa500','#40e0d0',
               '#1e90ff','#ff6347','#7b68ee','#00fa9a','#ffd700',
               '#6699FF','#ff6666','#30e0e0'],
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        legend: {
          data:['起始时间', '持续时间','终止时间']
        },
        // toolbox: {
        //     show : true,
        //     feature : {
        //         mark : {show: true},
        //         dataView : {show: true, readOnly: false},
        //         magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
        //         restore : {show: true},
        //         saveAsImage : {show: true}
        //     }
        // },
        calculable : true,
        grid: {
          left: '0%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        yAxis: [{
          type: 'category',
          data: yData,
          axisTick: {
            alignWithLabel: true
          }
        }],
        xAxis: [{
          type: 'value'
        }],
        series: [{
          name: '起始时间',
          type: 'bar',
          barWidth: '80%',
          stack: '总量',
          color:'rgba(128, 128, 128, 0.01)',
          itemStyle : { normal: {label : {show: false, position: 'insideRight'}}},
          data: sTime
        },{
          name: '持续时间',
          type: 'bar',
          barWidth: '80%',
          stack: '总量',
          color:'rgba(0, 128, 0, 0.91)',
          itemStyle : { normal: {label : {show: true, position: 'insideRight', formatter: '{b}起{a}{c}天'}}},
          data: tTime
        }]
      })
    }
  }
}
</script>
<style>
  .cl{
    display: -webkit-flex;
    display: flex;  
    -webkit-flex-flow: row wrap;
    flex-flow: row wrap;
  }
  .cl > *{
      flex: 9 0px;
  }
  .aside{
    flex: 4 0px;
    background-color: aquamarine;
  }
</style>
