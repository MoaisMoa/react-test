// Navbar.jsx — Link 컴포넌트 사용
import { Link } from 'react-router-dom';

function Navbar() {
  return (
    <header className="sticky top-0 z-20 border-b border-slate-200 bg-white/90 backdrop-blur">
      <nav className="mx-auto flex max-w-6xl items-center justify-between px-4 py-3 sm:px-6 lg:px-8">
        <div className="flex items-center gap-2">
          <Link
            to="/"
            className="rounded-lg px-3 py-2 text-sm font-medium text-slate-700 transition hover:bg-slate-100 hover:text-slate-900"
          >
            상품 목록
          </Link>
          <Link
            to="/ProductForm"
            className="rounded-lg bg-sky-600 px-3 py-2 text-sm font-semibold text-white transition hover:bg-sky-500"
          >
            상품 등록
          </Link>
        </div>
      </nav>
    </header>
  );
}

export default Navbar;