<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
    new Vue({
        el: '#app',
        data: function () {
            return {
                tableData: []
            }
        },
        mounted: function () {
            this.defaultActiveTag = 'article';
            this.init();
        },
        methods: {
            init: function () {
                this.$http.get('${request.contextPath}/admin/articleList?pageNum=1&pageSize=10').then(res => {
                    this.tableData = res.data;
                });
            },
            dateFormat: function (row, column, cellValue) {
                return new Date(cellValue).format('yyyy-MM-dd HH:mm')
            },
            handleClick(row) {
                console.log(row);
            }
        }
    })
</script>
</#assign>
<@main.page title="首页">
<el-table
        :data="tableData"
        border
        stripe>
    <el-table-column
            prop="title"
            label="标题">
    </el-table-column>
    <el-table-column
            prop="summary"
            show-overflow-tooltip
            max-width="300"
            label="摘要">
    </el-table-column>
    <el-table-column
            align="center"
            :formatter="dateFormat"
            prop="createTime"
            width="200"
            label="创建时间">
    </el-table-column>
    <el-table-column
            fixed="right"
            align="center"
            label="操作"
            width="100">
        <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
            <el-button type="text" size="small">编辑</el-button>
        </template>
    </el-table-column>
</el-table>
</@main.page>