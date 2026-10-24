import { Link, useNavigate } from "react-router-dom";
import { ShoppingCart, User as UserIcon, LogOut } from "lucide-react";
import { useAuthStore } from "../store/useAuthStore";

export default function Navbar() {
  const { user, logout } = useAuthStore();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav className="bg-white shadow-md sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16 items-center">
          {/* Logo */}
          <Link to="/" className="text-2xl font-bold text-blue-600">
            Need 4 Speed
          </Link>

          {/* Menu Links */}
          <div className="hidden md:flex space-x-8">
            <Link
              to="/"
              className="text-gray-700 hover:text-blue-600 font-medium"
            >
              Trang chủ
            </Link>
            <Link
              to="/vehicles"
              className="text-gray-700 hover:text-blue-600 font-medium"
            >
              Mua Xe
            </Link>
            <Link
              to="/accessories"
              className="text-gray-700 hover:text-blue-600 font-medium"
            >
              Phụ kiện
            </Link>
            <Link
              to="/services"
              className="text-gray-700 hover:text-blue-600 font-medium"
            >
              Bảo dưỡng
            </Link>
          </div>

          {/* Right side (Cart & Auth) */}
          <div className="flex items-center space-x-4">
            <Link
              to="/cart"
              className="text-gray-600 hover:text-blue-600 relative"
            >
              <ShoppingCart size={24} />
              {/* <span className="absolute -top-2 -right-2 bg-red-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center">3</span> */}
            </Link>

            {user ? (
              <div className="flex items-center space-x-4 ml-4 pl-4 border-l">
                <span className="text-sm font-medium text-gray-700 flex items-center gap-2">
                  <UserIcon size={18} /> {user.fullName}
                </span>
                <button
                  onClick={handleLogout}
                  className="text-red-500 hover:text-red-700"
                >
                  <LogOut size={20} />
                </button>
              </div>
            ) : (
              <div className="ml-4 pl-4 border-l space-x-2">
                <Link
                  to="/login"
                  className="text-blue-600 hover:text-blue-800 font-medium px-3 py-2"
                >
                  Đăng nhập
                </Link>
              </div>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}
