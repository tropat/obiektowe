import NIOSSL
import Fluent
import FluentSQLiteDriver
import Leaf
import Vapor

// configures your application
public func configure(_ app: Application) async throws {
    // uncomment to serve files from /Public folder
    // app.middleware.use(FileMiddleware(publicDirectory: app.directory.publicDirectory))
    try app.register(collection: ProductController())

    app.databases.use(.sqlite(.file("db.sqlite")), as: .sqlite)

    app.migrations.add(CreateProduct())

    try await app.autoMigrate()
  
    let productsData: [Product] = [
        Product(name: "Ksiazka", description:"ksiazka", price: 30.99),
        Product(name: "Pedzel", description:"pedzel", price: 2.50),
        Product(name: "Szklanka", description:"szklanka", price: 299.99)
    ]
    for product in productsData {
        try await product.create(on: app.db)
    }

    app.views.use(.leaf)
}
