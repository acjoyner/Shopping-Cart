# Cart.java Refactor Plan

## Problem
`Cart.java` (entity/model) contains business logic methods that belong in the service layer:
- `addItem(CartItem item)`
- `removeItem(CartItem item)`
- `updateTotalAmount()`

These mutate state and perform calculations — responsibilities of the service layer, not the JPA entity.

## Solution
Move the three methods from `Cart.java` to `CartService` (and `ICartService`), then update all callers.

## Files & Changes

### 1. `src/main/java/com/acjoyner/dream_shops/model/Cart.java`
**Remove** the three methods entirely. The class becomes a pure data entity.

### 2. `src/main/java/com/acjoyner/dream_shops/service/cart/ICartService.java`
**Add** three new interface methods:
```java
void addItem(Cart cart, CartItem item);
void removeItem(Cart cart, CartItem item);
void updateTotalAmount(Cart cart);
```

### 3. `src/main/java/com/acjoyner/dream_shops/service/cart/CartService.java`
**Implement** the three methods with the exact logic currently in `Cart.java`.

### 4. `src/main/java/com/acjoyner/dream_shops/service/cart/CartItemService.java`
**Update** three call sites:
- `cart.addItem(cartItem)` → `cartService.addItem(cart, cartItem)`
- `cart.removeItem(itemToRemove)` → `cartService.removeItem(cart, itemToRemove)`
- `cart.updateTotalAmount()` → `cartService.updateTotalAmount(cart)`

`CartItemService` already autowires `ICartService cartService`, so no new dependencies needed.

## Acceptance Criteria
- [ ] `Cart.java` no longer contains `addItem`, `removeItem`, or `updateTotalAmount`
- [ ] `CartService` implements all three methods with identical logic
- [ ] `CartItemService` calls `CartService` instead of `Cart` model methods
- [ ] `mvn compile` succeeds with zero errors
- [ ] `mvn test` passes (existing `DreamShopsApplicationTests`)

## Complexity
**Simple** — straightforward method relocation with straightforward caller updates. No concurrency, no new architecture.
