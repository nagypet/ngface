const PROXY_CONFIG = [
  {
    context: [
      "/demo",
      "/table-details",
    ],
    target: "http://localhost:8400",
    secure: false
  }
]

module.exports = PROXY_CONFIG;
