<p align="center">
	<strong>Imitation Douyin Mall</strong>
</p>
<p align="center">
	<strong>仿抖音商城，提供便捷、优质的购物环境，满足用户多样化的购物需求</strong>
</p>
<p align="center">
    <a target="blank" href="https://github.com/helltractor/imitation-douyin-mall">
        <img src="https://img.shields.io/github/stars/helltractor/imitation-douyin-mall.svg?style=social" alt="github star"/>
    </a>
    <a target="_blank" href="https://opensource.org/licenses/MIT">
        <img src="https://img.shields.io/:license-MIT-blue.svg" alt="license"/>
    </a>
    <a target="_blank" href="https://github.com/helltractor/imitation-douyin-mall">
        <img src='https://img.shields.io/badge/JDK-1.8.0_40+-green.svg' alt='jdk'/>
    </a>
</p>
<p align="center">
    <a target="blank" href="https://github.com/helltractor/imitation-douyin-mall">
        <img src='https://img.shields.io/badge/Maven-3.9.6-blue.svg' alt='maven'/>
    </a>
    <a target="_blank" href="https://github.com/helltractor/imitation-douyin-mall">
        <img src='https://img.shields.io/badge/Spring%20Boot-3.2.2-green.svg' alt='spring boot'/>
    </a>
    <a target="_blank" href="https://github.com/helltractor/imitation-douyin-mall">
        <img src='https://img.shields.io/badge/Spring%20Cloud-2023.0.1-green.svg' alt='spring boot'/>
    </a>
</p>

## Quick Start

### build container
```shell
docker-compose up -p ${project.name} -d
```

---

### start consul (singleton)
```shell
consul agent -server -bootstrap-expect=1 -ui -client=0.0.0.0
```

### start sentinel (singleton)
```shell
java -Dserver.port=8109 -Dcsp.sentinel.dashboard.server=localhost:8109 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.8.jar
```

## Dependencies Version
| Name                 | Version       |
|----------------------|---------------|
| Spring Boot          | 3.2.2         |
| Spring Cloud         | 2023.0.3      |
| Spring Cloud Alibaba | 2023.0.1.3    |
| Grpc Spring          | 3.1.0.RELEASE |
| Sentinel             | 1.8.8         |

## TODO