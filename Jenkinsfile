// 流水线的脚本
pipeline {
    // 所有CICD流程都需要在这里被定义

    // agent代理， 集群模式下表示任何一个机器可以用就执行。
    // 全局agent如果是none， 则每一个stage都要写agent
    agent any
    // 定义环境变量
    environment {
        name = "zhangsan"
        age = 17
        WS = "${WORKSPACE}"
    }

    // 定义一些环境信息

    // 定义流水线的加工流程
    stages {
        // 环境检查必须， 当环境
        stage('env') {
            steps {
                sh "java -version"
                sh "git --version"
                sh "docker version"
//                 sh "mvn -v"
                echo "env check done"
                echo "env check done"
            }
        }

        // 编译 -> 测试 -> 打包 -> 部署
        stage('compile') {
            // 每一个stage能单独配置要用的打包环境和命令
            // 需要额外的docker插件支持（dockerpipeline）
            // 每个stage会单独调用docker里面的镜像进行打包操作

            agent {
                docker {
                    image 'maven:3-alpine'
                    args '-v maven-repo:/root/.m2'
                }
            }
            steps {
            // 变量读取需要""的
//                 echo "compiling..."
//                 echo "${name}"
//                 echo "${age}"
//                 sh "pwd && ls -alh"
//                 sh "printenv"
                // 凡是需要取变量的位置， 都推荐写双引号
//                 sh "echo ${GIT_BRANCH}"
//                 echo "${GIT_BRANCH}"
                // jar包来推送给maven
                sh 'pwd && ls -alh'
                sh 'mvn -v'
                sh "echo ${WS}"
                // 每一行指令都和上下文环境有关，上下命令之间是独立的。
                // 因此这种命令需要 && 写在一起用。
                sh 'cd ${WS} && mvn clean package -s "/var/jenkins_home/appconfig/maven/settings.xml" -Dmaven.test.skip=true'
                sh "echo 打包成功"
            }
        }

        stage('test') {
            steps {
                echo "test..."
                echo "second test..."
            }
        }
        // 生成镜像
        stage('build') {
            steps {
                echo "build..."
                sh "docker version"
                sh "pwd && ls -alh"
                sh "docker build -t java-devops-demo . "
            }
        }

        stage("mailto") {
            steps {
                emailext body: '''<!DOCTYPE html>
                <html>
                <head>
                <meta charset="UTF-8">
                <title>${ENV, var="JOB_NAME"}-第${BUILD_NUMBER}次构建日志</title> </head>
                <body leftmargin="8" marginwidth="0" topmargin="8" marginheight="4" offset="0">
                <table width="95%" cellpadding="0" cellspacing="0" style="font-size: 11pt; font-family: Tahoma, Arial, Helvetica, sans-serif"> <h3>本邮件由系统自动发出，请勿回复!</h3>
                <tr> <br/>
                各位同事，大家好，以下为${PROJECT_NAME }项目构建信息</br>
                <td><font color="#CC0000">构建结果 - ${BUILD_STATUS}</font></td> </tr>
                <tr>
                            <td><br />
                <b><font color="#0B610B">构建信息</font></b>
                <hr size="2" width="100%" align="center" /></td> </tr>
                <tr> <td>
                href="${BUILD_URL}console">${BUILD_URL}console</a></li>
                <li>构建 Url : <a href="${BUILD_URL}">${BUILD_URL}</a></li>
                <li>工作目录 : <a href="${PROJECT_URL}ws">${PROJECT_URL}ws</a></li>
                </li>
                <li>项目 Url : <a href="${PROJECT_URL}">${PROJECT_URL}</a> </ul>
                <ul>
                <li>项目名称 : ${PROJECT_NAME}</li> <li>构建编号 : 第${BUILD_NUMBER}次构建</li> <li>触发原因: ${CAUSE}</li>
                <li>构建状态: ${BUILD_STATUS}</li> <li>构建日志: <a
                <h4><font color="#0B610B">最近提交</font></h4>
                <ul>
                <hr size="2" width="100%" />
                ${CHANGES_SINCE_LAST_SUCCESS, reverse=true, format="%c", changesFormat="<li>%d [%a] %m</li>"}
                </ul>
                详细提交: <a href="${PROJECT_URL}changes">${PROJECT_URL}changes</a><br/>
                </td> </tr>
                    </table>
                </body>
                </html>''', subject: '${PROJECT_NAME }项目第${BUILD_NUMBER}次构建通知', to: 'staninjapan2015@gmail.com'
            }
        }

        stage('deploy') {
            steps {
                echo "deploy..."
                // run之前把上一次的容器移除，命个名，这样就好对应操作了。
                sh "docker rm -f java-devops-demo-dev"
                sh "docker run -d -p 8888:8080 --name java-devops-demo-dev java-devops-demo"
            }
        }
    }

}