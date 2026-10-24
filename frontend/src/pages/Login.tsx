import { useState } from "react";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import axiosClient from "../api/axiosClient";
import { useAuthStore } from "../store/useAuthStore";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const login = useAuthStore((state) => state.login);
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const response: any = await axiosClient.post("/auth/login", {
        email,
        password,
      });

      const authData = response.data;

      if (!authData || !authData.accessToken) {
        toast.error("Lỗi: Không lấy được token từ API!");
        return;
      }

      const userData = {
        email: authData.email,
        fullName: authData.fullName,
        role: authData.role,
      };

      login(userData, authData.accessToken);

      toast.success("Đăng nhập thành công!");
      navigate("/");
    } catch (error: any) {
      const errorMsg =
        error.response?.data?.message || "Sai email hoặc mật khẩu!";
      toast.error(errorMsg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-16 bg-white p-8 border border-gray-200 rounded-xl shadow-sm">
      <h2 className="text-2xl font-bold text-center text-gray-900 mb-8">
        Đăng nhập tài khoản
      </h2>

      <form onSubmit={handleLogin} className="space-y-6">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Email
          </label>
          <input
            type="email"
            required
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="admin@carmarketplace.com"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            Mật khẩu
          </label>
          <input
            type="password"
            required
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="••••••••"
          />
        </div>

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-blue-600 text-white font-medium py-2.5 rounded-lg hover:bg-blue-700 transition disabled:bg-blue-400"
        >
          {loading ? "Đang xử lý..." : "Đăng nhập"}
        </button>
      </form>
    </div>
  );
}
