import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { deleteProduct, getProduct } from '../api/productApi';

function ProductDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    getProduct(id)
      .then((res) => {
        setProduct(res.data);
      })
      .catch(() => {
        setError('상품 정보를 불러오지 못했습니다.');
      })
  }, [id]);

  const handleDelete = () => {
    if (!id) return;
    if (!window.confirm('정말 삭제하시겠습니까?')) return;

    deleteProduct(id)
      .then(() => {
        alert('삭제되었습니다.');
        navigate('/');
      })
      .catch(() => {
        alert('삭제 중 오류가 발생했습니다.');
      });
  };

  if (error || !product) {
    return (
      <main className="mx-auto max-w-3xl px-4 py-8 sm:px-6 lg:px-8">
        <div className="rounded-2xl border border-rose-200 bg-rose-50 p-6 text-rose-700 shadow-sm">
          {error || '상품 정보를 찾을 수 없습니다.'}
        </div>
        <div className="mt-4">
          <button
            type="button"
            onClick={() => navigate('/')}
            className="inline-flex items-center justify-center rounded-lg bg-slate-700 px-4 py-2 text-sm font-medium text-white transition hover:bg-slate-600"
          >
            목록으로
          </button>
        </div>
      </main>
    );
  }

  return (
    <main className="mx-auto max-w-3xl px-4 py-8 sm:px-6 lg:px-8">
      <section className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
        <h1 className="text-2xl font-bold tracking-tight text-slate-900">상품 상세</h1>

        <dl className="mt-6 space-y-3 text-sm text-slate-700">
          <div className="flex items-center justify-between border-b border-slate-100 pb-2">
            <dt className="font-medium text-slate-500">상품명</dt>
            <dd className="font-semibold text-slate-900">{product.name}</dd>
          </div>
          <div className="flex items-center justify-between border-b border-slate-100 pb-2">
            <dt className="font-medium text-slate-500">가격</dt>
            <dd className="font-semibold text-slate-900">{Number(product.price).toLocaleString()}원</dd>
          </div>
          <div className="flex items-center justify-between border-b border-slate-100 pb-2">
            <dt className="font-medium text-slate-500">재고</dt>
            <dd className="font-semibold text-slate-900">{product.stock}개</dd>
          </div>
        </dl>

        <div className="mt-6 flex flex-wrap gap-2">
          <button
            type="button"
            onClick={() => navigate(`/products/${product.id}/edit`)}
            className="inline-flex items-center justify-center rounded-lg bg-sky-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-sky-500"
          >
            수정
          </button>
          <button
            type="button"
            onClick={handleDelete}
            className="inline-flex items-center justify-center rounded-lg bg-rose-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-rose-500"
          >
            삭제
          </button>
        </div>
      </section>
    </main>
  );
}

export default ProductDetail;
