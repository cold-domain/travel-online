module.exports = {
    lintOnSave: false,
    parallel: false,
    devServer: {
        host: "localhost",
        port: 21091,
        https: false,
        proxy: "http://localhost:21090",
        overlay: {
            warning: false,
            errors: false
        },
    }
}
