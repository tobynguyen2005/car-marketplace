import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";

const currentYear = new Date().getFullYear();

export default function Layout() {
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      <Navbar />
      {/* Nội dung các trang sẽ được render vào Outlet */}
      <main className="flex-grow w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <Outlet />
      </main>

      {/* Footer đơn giản */}
      <footer className="bg-gray-800 text-white text-center py-6 mt-auto">
        <p>© {currentYear} Need 4 Speed. All rights reserved.</p>
      </footer>
    </div>
  );
}
