// ProductForm.jsx — 등록/수정 모드 분기 처리 예시
import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getProduct, createProduct, updateProduct } from '../api/productApi';

function ProductForm() {
  const { id } = useParams();      // 수정 모드이면 id 존재, 등록 모드이면 undefined
  const navigate = useNavigate();
  const isEdit = !!id;             // true면 수정 모드

  const [form, setForm] = useState({ name: '', price: '', stock: '' });

  useEffect(() => {
    if (isEdit) {
      // 수정 모드: 기존 데이터 불러오기
      getProduct(id).then((res) => {
        const { name, price, stock } = res.data;
        setForm({
          name: name ?? '',
          price: price ?? '',
          stock: stock ?? '',
        });
      });
    }
  }, [id, isEdit]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const trimmedName = form.name.trim();
    const priceValue = Number(form.price);
    const stockValue = Number(form.stock);

    if (!trimmedName) {
      alert('상품명을 입력하세요.');
      return;
    }
    if (form.price === '' || !Number.isFinite(priceValue) || priceValue < 0) {
      alert('가격은 0 이상의 숫자를 입력하세요.');
      return;
    }
    if (form.stock === '' || !Number.isInteger(stockValue) || stockValue < 0) {
      alert('재고는 0 이상의 정수를 입력하세요.');
      return;
    }

    // 검사 통과 후 API 호출
    const payload = {
      ...form,
      name: trimmedName,
      price: priceValue,
      stock: stockValue,
    };

    if (isEdit) {
      updateProduct(id, payload).then(() => navigate('/'));
    } else {
      createProduct(payload).then(() => navigate('/'));
    }
  };

  return (
    <main className="mx-auto max-w-2xl px-4 py-8 sm:px-6 lg:px-8">
      <section className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
        <h1 className="text-2xl font-bold tracking-tight text-slate-900">{isEdit ? '상품 수정' : '상품 등록'}</h1>
        <p className="mt-1 text-sm text-slate-500">상품명, 가격, 재고 정보를 입력하세요.</p>

        <form onSubmit={handleSubmit} className="mt-6 space-y-4">
          <div className="space-y-1">
            <label htmlFor="name" className="text-sm font-medium text-slate-700">상품명</label>
            <input
              id="name"
              name="name"
              value={form.name}
              onChange={handleChange}
              placeholder="상품명"
              className="w-full rounded-lg border border-slate-300 px-3 py-2 text-slate-900 outline-none transition focus:border-sky-500 focus:ring-2 focus:ring-sky-200"/>
          </div>

          <div className="space-y-1">
            <label htmlFor="price" className="text-sm font-medium text-slate-700">가격</label>
            <input
              id="price"
              name="price"
              type="number"
              min="0"
              step="any"
              value={form.price}
              onChange={handleChange}
              placeholder="가격"
              className="w-full rounded-lg border border-slate-300 px-3 py-2 text-slate-900 outline-none transition focus:border-sky-500 focus:ring-2 focus:ring-sky-200"/>
          </div>

          <div className="space-y-1">
            <label htmlFor="stock" className="text-sm font-medium text-slate-700">재고</label>
            <input
              id="stock"
              name="stock"
              type="number"
              min="0"
              step="1"
              value={form.stock}
              onChange={handleChange}
              placeholder="재고"
              className="w-full rounded-lg border border-slate-300 px-3 py-2 text-slate-900 outline-none transition focus:border-sky-500 focus:ring-2 focus:ring-sky-200"/>
          </div>

          <div className="flex gap-3 pt-2">
            <button
              type="button"
              onClick={() => navigate('/')}
              className="inline-flex items-center justify-center rounded-lg bg-slate-200 px-4 py-2 text-sm font-medium text-slate-800 transition hover:bg-slate-300">
              취소
            </button>
            <button
              type="submit"
              className="inline-flex items-center justify-center rounded-lg bg-sky-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-sky-500">
              {isEdit ? '수정' : '등록'}
            </button>
          </div>
        </form>
      </section>
    </main>
  );
}

export default ProductForm;