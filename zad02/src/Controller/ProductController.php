<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

class ProductController extends AbstractController
{
    private $products = ["prod_1", "prod_2", "prod_3", "prod_4"];

    #[Route('/products', name: 'app_products', methods: ['GET'])]
    public function getProducts(): JsonResponse
    {
        return $this->json($this->products);
    }

    #[Route('/product/{id}', name: 'app_product_show', methods: ['GET'])]
    public function getProduct($id): JsonResponse
    {
        if ($id < 0 || $id >= count($this->products)) {
            return $this->json(['error' => 'Product not found'], 404);
        }
        return $this->json($this->products[$id]);
    }

    #[Route('/product', name: 'app_product_create', methods: ['POST'])]
    public function addProduct(Request $request): JsonResponse
    {
        $data = json_decode($request->getContent(), true);
        $this->products[] = $data['name'];
        return $this->json($this->products[count($this->products) - 1], 201);
    }

    #[Route('/product/{id}', name: 'app_product_update', methods: ['PUT'])]
    public function updateProduct($id, Request $request): JsonResponse
    {
        if ($id < 0 || $id >= count($this->products)) {
            return $this->json(['error' => 'Product not found'], 404);
        }
        $data = json_decode($request->getContent(), true);
        $this->products[$id] = $data['name'];
        return $this->json($this->products[$id]);
    }

    #[Route('/product/{id}', name: 'app_product_delete', methods: ['DELETE'])]
    public function deleteProduct($id): JsonResponse
    {
        if ($id < 0 || $id >= count($this->products)) {
            return $this->json(['error' => 'Product not found'], 404);
        }
        unset($this->products[$id]);
        return $this->json(null, 204);
    }
}
