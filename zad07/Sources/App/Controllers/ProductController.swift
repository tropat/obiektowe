import Vapor

struct ProductController: RouteCollection {
    func boot(routes: RoutesBuilder) throws {
        let productsRoute = routes.grouped("api", "products")
        productsRoute.get(use: getAllHandler)
        productsRoute.post(use: createHandler)
        productsRoute.group(":productID") { productRoute in
            productRoute.delete(use: deleteHandler)
        }
    }
    
    func getAllHandler(req: Request) throws -> EventLoopFuture<[Product]> {
        return Product.query(on: req.db).all()
    }

    func getHandler(req: Request) throws -> EventLoopFuture<Product> {
        guard let productID = req.parameters.get("productID", as: UUID.self) else {
            throw Abort(.badRequest)
        }
        return Product.find(productID, on: req.db)
            .unwrap(or: Abort(.notFound))
    }
    
    func createHandler(req: Request) throws -> EventLoopFuture<Product> {
        let product = try req.content.decode(Product.self)
        return product.save(on: req.db).map { product }
    }

    func updateHandler(req: Request) throws -> EventLoopFuture<Product> {
        guard let productID = req.parameters.get("productID", as: UUID.self) else {
            throw Abort(.badRequest)
        }
        let updatedProduct = try req.content.decode(Product.self)
        return Product.find(productID, on: req.db)
            .unwrap(or: Abort(.notFound))
            .flatMap { product in
                product.name = updatedProduct.name
                return product.update(on: req.db).transform(to: product)
            }
    }
    
    func deleteHandler(req: Request) throws -> EventLoopFuture<HTTPStatus> {
        return Product.find(req.parameters.get("productID"), on: req.db)
            .unwrap(or: Abort(.notFound))
            .flatMap { $0.delete(on: req.db) }
            .transform(to: .noContent)
    }
}
