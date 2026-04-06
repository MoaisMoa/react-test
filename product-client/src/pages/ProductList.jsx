import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getProducts, deleteProduct } from '../api/productApi.js';

function ProductList() {
  const [products, setProducts] = useState([]);
  const navigate = useNavigate();

  // 목록 불러오기
  const fetchProducts = () => {
    getProducts().then(res => setProducts(res.data));
  };

  useEffect(() => {
    fetchProducts();  // 컴포넌트 마운트 시 1회 실행
  }, []);

  // 삭제 처리
  const handleDelete = (id) => {
    if (window.confirm('정말 삭제하시겠습니까?')) {
      deleteProduct(id).then(() => fetchProducts());  // 삭제 후 목록 갱신
    }
  };

  const blob = new Blob()

  return (
    <main className="mx-auto max-w-6xl px-4 py-8 sm:px-6 lg:px-8">
      <div className="mb-6 flex items-end justify-between">
        <div>
          <h1 className="text-2xl font-bold tracking-tight text-slate-900">상품 목록</h1>
        </div>
      </div>

      {products.length === 0 ? (
        <div className="rounded-xl border border-dashed border-slate-300 bg-slate-50 p-10 text-center text-slate-500">
          등록된 상품이 없습니다.
        </div>
      ) : (
        <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
          {products.map((product) => (
            <article key={product.id} className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md">
              <h2 className="text-lg font-semibold text-slate-900">{product.name}</h2>
              <dl className="mt-3 space-y-1 text-sm text-slate-600">
                <div className="flex justify-between">
                  <dt>가격</dt>
                  <dd className="font-medium text-slate-800">{Number(product.price).toLocaleString()}원</dd>
                </div>
                <div className="flex justify-between">
                  <dt>재고</dt>
                  <dd className="font-medium text-slate-800">{product.stock}개</dd>
                </div>
              </dl>

              <div className="mt-5 flex gap-2">
                <button
                  type="button"
                  onClick={() => navigate(`/products/${product.id}`)}
                  className="inline-flex flex-1 items-center justify-center rounded-lg bg-slate-800 px-3 py-2 text-sm font-medium text-white transition hover:bg-slate-700"
                >
                  상세보기
                </button>
                <button
                  type="button"
                  onClick={() => handleDelete(product.id)}
                  className="inline-flex flex-1 items-center justify-center rounded-lg bg-rose-600 px-3 py-2 text-sm font-medium text-white transition hover:bg-rose-500"
                >
                  삭제
                </button>
              </div>
            </article>
          ))}
        </div>
      )}
    </main>
  );
}
export default ProductList;