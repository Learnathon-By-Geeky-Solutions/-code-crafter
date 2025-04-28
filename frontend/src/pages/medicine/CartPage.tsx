import { Link } from 'react-router-dom';
import { ShoppingCart, Trash2, Plus, Minus } from 'lucide-react';
import { useState } from 'react';

interface CartItem {
  medicine: {
    id: string;
    name: string;
    price: number;
    image: string;
  };
  quantity: number;
}

export function CartPage() {
  // Mock cart data - In production, this would come from a global state management solution
  const [cart, setCart] = useState<CartItem[]>([
    {
      medicine: {
        id: '1',
        name: 'Paracetamol 500mg',
        price: 9.99,
        image: 'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?auto=format&fit=crop&q=80&w=100&h=100'
      },
      quantity: 2
    }
  ]);
  const [couponCode, setCouponCode] = useState('');

  const updateQuantity = (medicineId: string, newQuantity: number) => {
    if (newQuantity < 1) {
      setCart(prevCart => prevCart.filter(item => item.medicine.id !== medicineId));
    } else {
      setCart(prevCart =>
        prevCart.map(item =>
          item.medicine.id === medicineId
            ? { ...item, quantity: newQuantity }
            : item
        )
      );
    }
  };

  const removeFromCart = (medicineId: string) => {
    setCart(prevCart => prevCart.filter(item => item.medicine.id !== medicineId));
  };

  const subtotal = cart.reduce((sum, item) => sum + (item.medicine.price * item.quantity), 0);
  const discount = couponCode === 'SAVE10' ? subtotal * 0.1 : 0;
  const total = subtotal - discount;

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-7xl mx-auto">
        <div className="flex gap-6">
          {/* Cart Items */}
          <div className="w-3/4">
            <h1 className="text-3xl font-bold mb-8 flex items-center gap-3">
              <ShoppingCart className="w-8 h-8" />
              Your Cart
            </h1>

            <div className="bg-white rounded-lg shadow-sm p-6">
              {cart.length === 0 ? (
                <div className="text-center py-8">
                  <p className="text-gray-500 mb-4">Your cart is empty</p>
                  <Link
                    to="/medicine"
                    className="text-blue-500 hover:text-blue-600 font-medium"
                  >
                    Continue Shopping
                  </Link>
                </div>
              ) : (
                <div className="space-y-6">
                  {cart.map((item) => (
                    <div key={item.medicine.id} className="flex items-center gap-4 pb-6 border-b">
                      <img
                        src={item.medicine.image}
                        alt={item.medicine.name}
                        className="w-20 h-20 object-cover rounded-lg"
                      />
                      <div className="flex-grow">
                        <h3 className="font-semibold text-lg">{item.medicine.name}</h3>
                        <p className="text-gray-600">৳{item.medicine.price} per unit</p>
                      </div>
                      <div className="flex items-center gap-6">
                        <div className="flex items-center gap-2">
                          <button
                            className="p-1 hover:bg-gray-100 rounded"
                            onClick={() => updateQuantity(item.medicine.id, item.quantity - 1)}
                          >
                            <Minus className="w-4 h-4" />
                          </button>
                          <span className="w-8 text-center">{item.quantity}</span>
                          <button
                            className="p-1 hover:bg-gray-100 rounded"
                            onClick={() => updateQuantity(item.medicine.id, item.quantity + 1)}
                          >
                            <Plus className="w-4 h-4" />
                          </button>
                        </div>
                        <button
                          className="text-red-500 hover:text-red-700"
                          onClick={() => removeFromCart(item.medicine.id)}
                        >
                          <Trash2 className="w-5 h-5" />
                        </button>
                      </div>
                    </div>
                  ))}

                  <div className="flex justify-between items-center pt-4">
                    <Link
                      to="/medicine"
                      className="text-blue-500 hover:text-blue-600 font-medium"
                    >
                      Continue Shopping
                    </Link>
                  </div>
                </div>
              )}
            </div>
          </div>

          {/* Order Summary */}
          <div className="w-1/4">
            <div className="bg-white rounded-lg shadow-sm p-6 sticky top-4">
              <h2 className="text-xl font-semibold mb-6">Order Summary</h2>

              <div className="mb-6">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Apply Coupon
                </label>
                <div className="flex gap-2">
                  <input
                    type="text"
                    className="flex-grow p-2 border rounded-lg"
                    value={couponCode}
                    onChange={(e) => setCouponCode(e.target.value)}
                    placeholder="Enter coupon code"
                  />
                  <button
                    className="px-4 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200"
                    onClick={() => {/* Handle coupon validation */}}
                  >
                    Apply
                  </button>
                </div>
                {couponCode === 'SAVE10' && (
                  <p className="text-green-500 text-sm mt-1">10% discount applied!</p>
                )}
              </div>

              <div className="space-y-2 text-sm">
                <div className="flex justify-between">
                  <span>Subtotal</span>
                  <span>৳{subtotal.toFixed(2)}</span>
                </div>
                {discount > 0 && (
                  <div className="flex justify-between text-green-500">
                    <span>Discount</span>
                    <span>-৳{discount.toFixed(2)}</span>
                  </div>
                )}
                <div className="flex justify-between font-semibold text-lg pt-2 border-t">
                  <span>Total</span>
                  <span>৳{total.toFixed(2)}</span>
                </div>
              </div>

              <Link
                to="/medicine/checkout"
                className="block w-full text-center bg-blue-500 text-white py-3 rounded-lg mt-6 hover:bg-blue-600 transition-colors"
              >
                Proceed to Checkout
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}