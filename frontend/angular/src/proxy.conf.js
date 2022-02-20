const PROXY_CONFIG = [
    {
        context: [
            "/demo",
        ],
        target: "http://localhost:8400",
        secure: false
    }
]

module.exports = PROXY_CONFIG;
