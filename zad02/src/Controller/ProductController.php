<?php

namespace App\Controller;

use App\Entity\Product;
use App\Repository\ProductRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\Persistence\ManagerRegistry as PersistenceManagerRegistry;

    
class ProductController extends AbstractController
{
    #[Route('/products', name: 'app_products', methods: ['GET'])]
    public function getProducts(ProductRepository $productRepository): JsonResponse
    {
        $products = $productRepository->findAll();
        return $this->json($products);
    } 

    #[Route('/product/{id}', name: 'app_product_show', methods: ['GET'])]
    public function getProduct(ProductRepository $productRepository, $id, PersistenceManagerRegistry $doctrine): JsonResponse
    {
        $product = $productRepository->find($id);

        if (!$product) {
            return $this->json(['error' => 'Product not found'], 404);
        }

        return $this->json($product);
    }

    #[Route('/product', name: 'app_product_create', methods: ['POST'])]
    public function addProduct(Request $request, PersistenceManagerRegistry $doctrine): JsonResponse
    {
        $data = json_decode($request->getContent(), true);

        $entityManager = $doctrine->getManager();
        $product = new Product();
        $product->setName($data['name']);

        $entityManager->persist($product);
        $entityManager->flush();

        return $this->json($product, 201);
    }

    #[Route('/product/{id}', name: 'app_product_update', methods: ['PUT'])]
    public function updateProduct(ProductRepository $productRepository, $id, Request $request, PersistenceManagerRegistry $doctrine): JsonResponse
    {
        $product = $productRepository->find($id);

        if (!$product) {
            return $this->json(['error' => 'Product not found'], 404);
        }

        $data = json_decode($request->getContent(), true);
        $product->setName($data['name']);

        $entityManager = $doctrine->getManager();
        $entityManager->persist($product);
        $entityManager->flush();

        return $this->json($product);
    }

    #[Route('/product/{id}', name: 'app_product_delete', methods: ['DELETE'])]
    public function deleteProduct(ProductRepository $productRepository, $id, PersistenceManagerRegistry $doctrine): JsonResponse
    {
        $product = $productRepository->find($id);

        if (!$product) {
            return $this->json(['error' => 'Product not found'], 404);
        }

        $entityManager = $doctrine->getManager();
        $entityManager->remove($product);
        $entityManager->flush();

        return $this->json(null, 204);
    }
}

