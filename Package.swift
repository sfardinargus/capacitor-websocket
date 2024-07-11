// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorWebsocket",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorWebsocket",
            targets: ["WebsocketPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "WebsocketPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/WebsocketPlugin"),
        .testTarget(
            name: "WebsocketPluginTests",
            dependencies: ["WebsocketPlugin"],
            path: "ios/Tests/WebsocketPluginTests")
    ]
)