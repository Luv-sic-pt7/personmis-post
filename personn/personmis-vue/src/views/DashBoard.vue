<template>
<!--  <NavMain></NavMain>-->
  <div class="analysis-page">
    <!-- 顶部统计卡片区域 -->
    <el-row :gutter="20" class="top-metrics" type="flex" justify="start" align="middle">
      <el-col :span="4">
        <el-card shadow="hover">
          <div class="metric-title">岗位总数</div>
          <div class="metric-value">{{postCount}}</div>
          <div class="metric-sub">week ratio 12% <span style="color: #52c41a;">↑</span></div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card shadow="hover">
          <div class="metric-title">员工总数</div>
          <div class="metric-value">{{staffCount}}</div>
          <div class="metric-footer">Day Raises 12</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card shadow="hover">
          <div class="metric-title">平均任职时长（月）</div>
          <div class="metric-value">{{averageTenure}}</div>
          <div class="metric-footer">Quit Rate 10%</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card shadow="hover">
          <div class="metric-title">平均日工作时长（小时）</div>
          <div class="metric-value">{{averageTaskDuration}}</div>
          <div class="metric-sub">week ratio 15% <span style="color: #52c41a;">↑</span></div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card shadow="hover">
          <div class="metric-title">总月薪资（千元）</div>
          <div class="metric-value">¥{{totalMonthlySalary}}</div>
          <div class="metric-sub">week ratio 2% <span style="color: #52c41a;">↑</span></div>
        </el-card>
      </el-col>
      <el-col :span="4" class="button-container">
        <el-button
            @click="goToHomePage"
            type="primary"
            icon="el-icon-arrow-left"
            round
            size="medium">
          返回首页
        </el-button>
      </el-col>
    </el-row>


    <!-- 图表和排名信息 -->
    <el-row :gutter="20" style="margin-top:20px;">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="chart-header performance-header">
              <span class="performance-title">岗位绩效分析</span>
              <div class="filter-bar">
                <el-select v-model="selectedPost" placeholder="选择岗位" @change="fetchChartData">
                  <el-option
                      v-for="post in posts"
                      :key="post.id"
                      :label="post.pname"
                      :value="post.pname"></el-option>
                </el-select>
              </div>
            </div>
          </template>
          <div ref="mainChart" style="width: 100%; height: 290px;"></div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="chart-header">
              <span class="salary-title">岗位平均薪资排行</span>
            </div>
          </template>
          <div class="salary-ranking">
            <div class="ranking-item" v-for="(item, index) in salaryRank" :key="index">
              <div class="rank-number">{{ index + 1 }}</div>
              <div class="rank-info">
                <div class="rank-name">{{ item.postName }}</div>
                <div class="rank-salary">¥{{ item.averageSalary.toFixed(2) }}</div>
              </div>
            </div>
          </div>
        </el-card>

      </el-col>
    </el-row>

    <!-- 下方示意区域，可自行修改或删除 -->
    <el-row :gutter="20" style="margin-top:5px;">
      <el-col :span="12">
        <el-card>
          <template #header>岗位员工结构</template>
          <el-row>
            <el-col :span="24">
              <div style="text-align: center; font-weight: bold;">岗位年龄结构</div>
            </el-col>
          </el-row>
          <el-select v-model="selectedPostForPie1" placeholder="选择岗位" @change="fetchPieChartData1">
            <el-option v-for="post in posts" :key="post.id" :label="post.pname" :value="post.pname"></el-option>
          </el-select>
          <div ref="pieChart1" style="width: 100%; height: 300px;"></div>
          <el-row>
            <el-col :span="24">
              <div style="text-align: center; font-weight: bold;">岗位学历结构</div>
            </el-col>
          </el-row>
          <el-select v-model="selectedPostForPie2" placeholder="选择岗位" style="margin-top: 20px;" @change="fetchPieChartData2">
            <el-option v-for="post in posts" :key="post.id" :label="post.pname" :value="post.pname"></el-option>
          </el-select>
          <div ref="pieChart2" style="width: 100%; height: 300px; margin-top: 20px;"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>岗位流动情况</template>
          <el-row>
            <el-col :span="24">
              <div style="text-align: center; font-weight: bold;">离职率</div>
            </el-col>
          </el-row>
          <el-select v-model="selectedPostForGauge1" placeholder="选择岗位" @change="fetchTurnoverRate">
            <el-option v-for="post in posts" :key="post.id" :label="post.pname" :value="post.id"></el-option>
          </el-select>
          <div ref="gaugeChart1" style="width: 100%; height: 310px;"></div>
          <el-row>
            <el-col :span="24">
              <div style="text-align: center; font-weight: bold;">调动率</div>
            </el-col>
          </el-row>
          <el-select v-model="selectedPostForGauge2" placeholder="选择岗位" @change="fetchTransferRate">
            <el-option v-for="post in posts" :key="post.id" :label="post.pname" :value="post.id"></el-option>
          </el-select>
          <div ref="gaugeChart2" style="width: 100%; height: 310px; margin-top: 20px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import axios from 'axios'
// import NavMain from "@/components/NavMain.vue";
export default {
  // components: {NavMain},
  data() {
    return {
      postCount: 0,
      staffCount: 0,
      averageTenure: 0,
      averageTaskDuration: 0,
      totalMonthlySalary: 0,
      timeRange: 'all_year',
      dateRange: ['2018-01-01', '2018-12-31'],
      chartTab: 'sales',
      salaryRank: [],
      posts: [],
      selectedPost: '',
      chartData: [],
      selectedPostForGauge1: '',
      selectedPostForGauge2: '',
      gaugeChart1Instance: null,
      gaugeChart2Instance: null,
      mainChartInstance: null,
      pieChart1Instance: null,
      pieChart2Instance: null,
      selectedPostForPie1: '',
      selectedPostForPie2: ''
    }
  },
  mounted() {
    this.initMainChart();
    this.initSmallCharts();
    this.initPieCharts();
    this.fetchPostCount(); // 调用方法获取岗位总数
    this.fetchStaffCount(); // 调用方法获取在岗人数
    this.fetchAverageTenure(); // 调用方法获取平均任职时长
    this.fetchAverageTaskDuration(); // 调用方法获取平均任务时长
    this.fetchTotalMonthlySalary(); // 调用方法获取总月薪资
    this.fetchPosts(); // 调用方法获取岗位列表
    this.fetchAverageSalaryByPost(); // 调用方法获取岗位平均薪资排行
    this.initGaugeCharts(); // 调用方法初始化仪表盘
    this.fetchTurnoverRate(); // 调用方法获取离职率
    this.fetchTransferRate(); // 调用方法获取转正率
    window.addEventListener('resize', this.handleResize);
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.handleResize);
  },
  methods: {
    goToHomePage() {
      this.$router.push('/adddepartment');  // 跳转到主页路径
    },
    fetchPostCount() {
      axios.get('/getPostCount')
          .then(response => {
            this.postCount = response.data;
          })
          .catch(error => {
            console.error('Error fetching post count:', error);
          });
    },
    fetchStaffCount() {
      axios.get('/getStaffCount')
          .then(response => {
            this.staffCount = response.data;
          })
          .catch(error => {
            console.error('Error fetching staff count:', error);
          });
    },
    fetchAverageTenure() {
      axios.get('/getAverageTenure')
          .then(response => {
            this.averageTenure = response.data.toFixed(2);
          })
          .catch(error => {
            console.error('Error fetching average tenure:', error);
          });
    },
    fetchAverageTaskDuration() {
      axios.get('/getAverageTaskDuration')
          .then(response => {
            this.averageTaskDuration = response.data.toFixed(2);
          })
          .catch(error => {
            console.error('Error fetching average task duration:', error);
          });
    },
    fetchTotalMonthlySalary() {
      axios.get('/getTotalMonthlySalary')
          .then(response => {
            this.totalMonthlySalary = response.data.toFixed(2);
          })
          .catch(error => {
            console.error('Error fetching total monthly salary:', error);
          });
    },
    fetchPosts() {
      axios.get('/getPosts')
          .then(response => {
            this.posts = response.data;
            if (this.posts.length > 0) {
              this.selectedPost = this.posts[0].pname;
              this.selectedPostForPie1 = this.posts[0].pname;
              this.selectedPostForPie2 = this.posts[0].pname;
              this.selectedPostForGauge1 = this.posts[0].id;
              this.selectedPostForGauge2 = this.posts[0].id;
              this.fetchChartData();
              this.fetchPieChartData1();
              this.fetchPieChartData2();
              this.fetchTurnoverRate();
              this.fetchTransferRate();
            }
          })
          .catch(error => {
            console.error('Error fetching posts:', error);
          });
    },
    fetchChartData() {
      const selectedPost = this.posts.find(post => post.pname === this.selectedPost);
      if (selectedPost) {
        axios.get(`/getWorkloadByPost?postId=${selectedPost.id}`)
            .then(response => {
              const data = response.data;
              const months = Array(12).fill(0);
              data.forEach(item => {
                months[item.month - 1] = item.totalTasks;
              });
              this.chartData = months;
              this.renderMainChart(months);
              this.fetchPieChartData1();
              this.fetchPieChartData2();
            })
            .catch(error => {
              console.error('Error fetching chart data:', error);
            });
      }
    },
    fetchTurnoverRate() {
      if (this.selectedPostForGauge1) {
        axios.get(`/getTurnoverRate?postId=${this.selectedPostForGauge1}`)
            .then(response => {
              const rate = parseFloat(response.data); // Convert to a number
              if (!isNaN(rate)) { // Check if it's a valid number
                this.renderGaugeChart(this.gaugeChart1Instance, rate * 100);
              } else {
                console.error('Invalid turnover rate received:', response.data);
                // Optionally, set a default value or handle the error in the chart
                this.renderGaugeChart(this.gaugeChart1Instance, 0); // Example: Set to 0
              }
            })
            .catch(error => {
              console.error('Error fetching turnover rate:', error);
              // Handle the error, perhaps by displaying an error message in the chart
            });
      }
    },

    fetchTransferRate() {
      if (this.selectedPostForGauge2) {
        axios.get(`/getTransferRate?postId=${this.selectedPostForGauge2}`)
            .then(response => {
              const rate = parseFloat(response.data);
              if (!isNaN(rate)) {
                this.renderGaugeChart(this.gaugeChart2Instance, rate * 100);
              } else {
                console.error('Invalid transfer rate received:', response.data);
                this.renderGaugeChart(this.gaugeChart2Instance, 0); // Example: Set to 0
              }
            })
            .catch(error => {
              console.error('Error fetching transfer rate:', error);
              // Handle the error appropriately
            });
      }
    },
    initMainChart() {
      const el = this.$refs.mainChart;
      if(!el) return;
      this.mainChartInstance = echarts.init(el);
      this.renderMainChart();
    },
    renderMainChart() {
      const option = {
        tooltip: {},
        xAxis: {
          type: 'category',
          data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: this.chartData,
          type: 'bar',
          itemStyle: {
            color: '#1890ff'
          }
        }]
      };
      this.mainChartInstance.setOption(option);
    },
    fetchAverageSalaryByPost() {
      axios.get('/getAverageSalaryByPost')
          .then(response => {
            this.salaryRank = response.data;
          })
          .catch(error => {
            console.error('Error fetching average salary by post:', error);
          });
    },
    initSmallCharts() {
      // Visits小图表
      const visitsEl = this.$refs.visitsChart;
      if(visitsEl) {
        this.visitsChartInstance = echarts.init(visitsEl);
        this.visitsChartInstance.setOption({
          xAxis: {show:false},
          yAxis: {show:false},
          series: [{
            type: 'line',
            data: [800, 1000, 900, 1100, 1200, 850, 600],
            smooth: true,
            lineStyle: {color: '#722ed1'},
            showSymbol: false
          }],
          grid: {left: 0, right: 0, top: 0, bottom: 0}
        });
      }

      // Payments小图表
      const paymentsEl = this.$refs.paymentsChart;
      if (paymentsEl) {
        this.paymentsChartInstance = echarts.init(paymentsEl);
        this.paymentsChartInstance.setOption({
          xAxis: {show: false},
          yAxis: {show: false},
          series: [{
            type: 'bar',
            data: [500, 600, 700, 800, 700, 600, 500],
            itemStyle: {color: '#13c2c2'}
          }],
          grid: {left: 0, right: 0, top: 0, bottom: 0}
        });
      }

      // Operational Effect小图表
      const opEl = this.$refs.opChart;
      if (opEl) {
        this.opChartInstance = echarts.init(opEl);
        this.opChartInstance.setOption({
          xAxis: {show: false},
          yAxis: {show: false},
          series: [{
            type: 'line',
            data: [70, 72, 73, 75, 78, 77, 78],
            smooth: true,
            lineStyle: {color: '#faad14'},
            showSymbol: false
          }],
          grid: {left: 0, right: 0, top: 0, bottom: 0}
        });
      }
    },
    initPieCharts() {
      const pieChart1El = this.$refs.pieChart1;
      const pieChart2El = this.$refs.pieChart2;
      if (pieChart1El) {
        this.pieChart1Instance = echarts.init(pieChart1El);
        this.renderPieChart(this.pieChart1Instance);
      }
      if (pieChart2El) {
        this.pieChart2Instance = echarts.init(pieChart2El);
        this.renderPieChart(this.pieChart2Instance);
      }
    },
    fetchPieChartData1() {
      const selectedPost = this.selectedPostForPie1;
      if (selectedPost) {
        axios.get(`/getPieChartData1?postName=${selectedPost}`)
            .then(response => {
              const data = response.data[0]; // Access the first (and only) element
              const chartData = [
                { value: data.under_20, name: "under 20" },
                { value: data['20_30'], name: "20-30" },  // Bracket notation for key with hyphen
                { value: data['30_40'], name: "30-40" },
                { value: data.over_40, name: "above 40" }
              ];
              this.renderPieChart(this.pieChart1Instance, chartData);
            })
            .catch(error => {
              console.error('Error fetching pie chart data 1:', error);
            });
      }
    },
    fetchPieChartData2() {
      const selectedPost = this.selectedPostForPie2;
      if (selectedPost) {
        axios.get(`/getEducationDistribution?postName=${selectedPost}`)
            .then(response => {
              const data = response.data.map(item => ({
                value: item.count,
                name: item.education
              }));
              this.renderPieChart(this.pieChart2Instance, data);
            })
            .catch(error => {
              console.error('Error fetching pie chart data 2:', error);
            });
      }
    },
    renderPieChart(chartInstance, data = []) {
      const option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 40,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: data
          }
        ]
      };
      chartInstance.setOption(option);
    },
    initGaugeCharts() {
      const gaugeChart1El = this.$refs.gaugeChart1;
      const gaugeChart2El = this.$refs.gaugeChart2;
      if (gaugeChart1El) {
        this.gaugeChart1Instance = echarts.init(gaugeChart1El);
      }
      if (gaugeChart2El) {
        this.gaugeChart2Instance = echarts.init(gaugeChart2El);
      }
    },
    renderGaugeChart(chartInstance, value) {
      const option = {
        tooltip: {
          formatter: '{a} <br/>{b} : {c}%'
        },
        series: [
          {
            name: 'Rate',
            type: 'gauge',
            progress: {
              show: true
            },
            detail: {
              valueAnimation: true,
              formatter: '{value}%'
            },
            data: [
              {
                value: value,
                name: 'Rate'
              }
            ]
          }
        ]
      };
      chartInstance.setOption(option);
    },
    handleResize() {
      if (this.mainChartInstance) this.mainChartInstance.resize();
      if (this.visitsChartInstance) this.visitsChartInstance.resize();
      if (this.paymentsChartInstance) this.paymentsChartInstance.resize();
      if (this.opChartInstance) this.opChartInstance.resize();
      if (this.pieChart1Instance) this.pieChart1Instance.resize();
      if (this.pieChart2Instance) this.pieChart2Instance.resize();
    },
    watch: {
      selectedPostForPie1() {
        this.fetchPieChartData1();
      },
      selectedPostForPie2() {
        this.fetchPieChartData2();
      }
    }
  }
}
</script>

<style scoped>
.analysis-page {
  padding: 20px;
  background: #f0f2f5;
  height: 100vh;
  overflow-y: auto;
}

/* 顶部统计卡片 */
.top-metrics {
  margin-bottom: 20px;
}

.metric-title {
  font-size: 14px;
  color: #999;
}

.metric-value {
  font-size: 24px;
  font-weight: bold;
  margin: 5px 0;
}

/* 样式部分 */
.filter-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.time-range-buttons {
  margin-left: 100px; /* 将整个按钮组整体向右移动30px，您可根据需要调整数值 */
  display: flex;
  gap: 20px; /* 按钮之间增加20px的间距 */
}

.date-range-picker {
  /* 如果需要，可以对日期选择器本身加些额外间距 */
  margin-left: 20px;
}

.metric-sub {
  font-size: 12px;
  color: #999;
}

.metric-footer {
  font-size: 12px;
  color: #999;
  margin-top: 10px;
}

/* 筛选条件 */
.filter-bar {
  margin-bottom: 20px;
}

.filter-bar .el-button-group .el-button.active {
  border-bottom: 2px solid #1890ff;
  color: #1890ff;
}

/* 图表和排行 */
.chart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.chart-header .el-button-group .el-button.active {
  border-bottom: 2px solid #1890ff;
  color: #1890ff;
}

.salary-ranking {
  padding: 10px 0;
  overflow-y: auto;
}

.ranking-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.rank-number {
  width: 30px;
  height: 30px;
  background: #f78f46;
  color: #fff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  margin-right: 10px;
  font-size: 14px;
}

.rank-info {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 80%;
}

.rank-name {
  margin-left: 20px;  /* 仅对岗位名称偏移 */
  font-size: 14px;
  color: #333;
}

.rank-salary {
  font-size: 14px;
  color: #999;
}
.salary-ranking {
  padding: 10px 0;
  max-height: 307px; /* 根据需求可自行调整高度 */
  overflow-y: auto;  /* 出现滚动条 */
}
.chart-header {
  display: flex;
  justify-content: flex-end; /* 标题整体向右 */
  align-items: center;
}

.salary-title {
  font-family: "Microsoft YaHei", sans-serif;
  font-weight: bold;
  font-size: 16px;
  color: #333;
  margin-right: 130px; /* 如果希望额外往右挪，可以增加margin */
}
.performance-header {
  display: flex;
  align-items: center;
  /* 标题保持在左侧，不随前面修改而移动 */
}

.performance-title {
  font-family: "Microsoft YaHei", sans-serif; /* 使用雅黑字体 */
  font-weight: bold;                         /* 加粗 */
  font-size: 20px;                           /* 调整字号 */
  color: #333;
  margin-right: 200px;                        /* 标题与选择框之间留出间距 */
}

.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.el-button {
  background-color: #1890ff; /* 自定义按钮颜色 */
  border: none;
  color: #fff;
  font-weight: bold;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
  transition: all 0.3s ease;
}

.el-button:hover {
  background-color: #40a9ff; /* 鼠标悬浮时的颜色 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}
</style>