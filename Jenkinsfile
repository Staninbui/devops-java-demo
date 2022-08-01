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
                sh 'mvn clean package -s "/var/jenkins_home/appconfig/maven/settings.xml" -Dmaven.test.skip=true'
                sh "echo 打包成功"
            }
        }

        stage('test') {
            steps {
                echo "test..."
                echo "second test..."
            }
        }
        stage('build') {
            steps {
                echo "build..."
            }
        }
        stage('deploy') {
            steps {
                echo "deploy..."
            }
        }
    }
}