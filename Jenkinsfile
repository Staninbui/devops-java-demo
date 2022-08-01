// 流水线的脚本
pipeline {
    // 所有CICD流程都需要在这里被定义

    // agent代理， 集群模式下表示任何一个机器可以用就执行。
    agent any
    // 定义环境变量
    environment {
        name = "zhangsan"
        age = 17
    }

    // 定义一些环境信息

    // 定义流水线的加工流程
    stages {
        // 编译 -> 测试 -> 打包 -> 部署
        stage('compile') {
            steps {
            // 变量读取需要""的
                echo "compiling..."
                echo "${name}"
                echo "${age}"
                sh "pwd && ls -alh"
                sh "printenv"
                sh "echo ${GIT_BRANCH}"
                echo "${GIT_BRANCH}"
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