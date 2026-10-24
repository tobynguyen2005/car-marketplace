import { Link } from "react-router-dom";
import {
  PhoneCall,
  Shield,
  Gauge,
  Zap,
  Flame,
  ArrowUpRight,
  MessageSquare,
} from "lucide-react";

const featuredCars = [
  {
    id: 1,
    name: "Mercedes-AMG GT Black Series",
    price: "18,500,000,000 ₫",
    image:
      "https://images.unsplash.com/photo-1617531653332-bd46c24f2068?q=80&w=2115&auto=format&fit=crop",
    type: "Coupe Performance",
    hp: "720 HP",
    speed: "3.2s (0-100km/h)",
    engine: "4.0L V8 Biturbo",
  },
  {
    id: 2,
    name: "Porsche 911 GT3 RS (992)",
    price: "19,900,000,000 ₫",
    image:
      "https://images.unsplash.com/photo-1503376712351-1c43b9e15641?q=80&w=2070&auto=format&fit=crop",
    type: "Track Weapon",
    hp: "525 HP",
    speed: "3.2s (0-100km/h)",
    engine: "4.0L Atmospheric V6",
  },
  {
    id: 3,
    name: "Lamborghini Huracán STO",
    price: "22,000,000,000 ₫",
    image:
      "https://images.unsplash.com/photo-1629897039644-83907ebc07eb?q=80&w=2070&auto=format&fit=crop",
    type: "Supercar",
    hp: "640 HP",
    speed: "3.0s (0-100km/h)",
    engine: "5.2L Naturally Aspirated V10",
  },
];

export default function Home() {
  return (
    <div className="bg-neutral-950 text-neutral-100 min-h-screen font-sans selection:bg-red-600 selection:text-white">
      {/* 1. HERO SECTION: Aggressive Performance Aesthetic */}
      <section className="relative min-h-[90vh] flex items-center justify-center overflow-hidden border-b border-neutral-800">
        {/* Background Image với hiệu ứng Dark Overlay */}
        <div className="absolute inset-0">
          <img
            src="https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?q=80&w=2070&auto=format&fit=crop"
            alt="Supercar Hero"
            className="w-full h-full object-cover opacity-35 scale-105"
          />
          <div className="absolute inset-0 bg-gradient-to-t from-neutral-950 via-neutral-950/60 to-transparent" />
          <div className="absolute inset-0 bg-[radial-gradient(ellipse_at_center,_var(--tw-gradient-stops))] from-transparent via-neutral-950/80 to-neutral-950" />
        </div>

        <div className="relative max-w-7xl mx-auto px-6 py-24 text-center">
          <div className="inline-flex items-center gap-2 px-4 py-1.5 rounded-full bg-red-950/80 border border-red-800/50 text-red-500 text-xs font-mono tracking-widest uppercase mb-8 backdrop-blur-md">
            <Flame size={14} className="animate-pulse" /> Supercar &
            High-Performance Garage
          </div>

          <h1 className="text-5xl sm:text-7xl lg:text-8xl font-black tracking-tighter uppercase italic leading-none mb-6">
            BẢN NĂNG{" "}
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-red-500 via-orange-500 to-amber-400">
              TỐC ĐỘ
            </span>
          </h1>

          <p className="text-lg sm:text-xl text-neutral-400 max-w-2xl mx-auto font-light mb-10 leading-relaxed">
            Độc quyền cung cấp các siêu phẩm tốc độ, xe đua đường phố và các bản
            độ giới hạn dành riêng cho dân chơi thực thụ.
          </p>

          <div className="flex flex-col sm:flex-row gap-4 justify-center items-center">
            <Link
              to="/vehicles"
              className="w-full sm:w-auto px-8 py-4 bg-red-600 hover:bg-red-700 text-white font-extrabold uppercase tracking-wider text-sm transition-all duration-300 shadow-lg shadow-red-900/40 flex items-center justify-center gap-2 group"
            >
              Khám phá Garage{" "}
              <ArrowUpRight
                size={18}
                className="group-hover:translate-x-1 group-hover:-translate-y-1 transition-transform"
              />
            </Link>

            <a
              href="tel:0999999999"
              className="w-full sm:w-auto px-8 py-4 bg-neutral-900 border border-neutral-800 hover:border-neutral-700 text-neutral-200 font-bold uppercase tracking-wider text-sm transition-all flex items-center justify-center gap-2"
            >
              <PhoneCall size={16} className="text-red-500" /> Hot Line Tư Vấn
              VIP
            </a>
          </div>
        </div>
      </section>

      {/* 2. BẢNG THÔNG SỐ VẬN HÀNH (PERFORMANCE HIGHLIGHTS) */}
      <section className="py-20 border-b border-neutral-800 bg-neutral-900/50">
        <div className="max-w-7xl mx-auto px-6 grid grid-cols-1 md:grid-cols-3 gap-8">
          <div className="p-8 border border-neutral-800 bg-neutral-950/80 relative overflow-hidden group hover:border-red-900/50 transition duration-500">
            <div className="absolute top-0 right-0 w-24 h-24 bg-red-600/5 blur-2xl group-hover:bg-red-600/20 transition" />
            <Gauge size={36} className="text-red-500 mb-6" />
            <h3 className="text-xl font-extrabold uppercase tracking-wide text-white mb-2">
              Kiểm Định Dyno & Track
            </h3>
            <p className="text-neutral-400 text-sm leading-relaxed">
              Mọi chiếc xe trước khi bàn giao đều được đo công suất thực tế trên
              Dyno và chạy thử nghiệm tại đường đua tiêu chuẩn.
            </p>
          </div>

          <div className="p-8 border border-neutral-800 bg-neutral-950/80 relative overflow-hidden group hover:border-red-900/50 transition duration-500">
            <div className="absolute top-0 right-0 w-24 h-24 bg-red-600/5 blur-2xl group-hover:bg-red-600/20 transition" />
            <Zap size={36} className="text-amber-500 mb-6" />
            <h3 className="text-xl font-extrabold uppercase tracking-wide text-white mb-2">
              Độ Xe Stage 1 - Stage 3
            </h3>
            <p className="text-neutral-400 text-sm leading-relaxed">
              Hỗ trợ remap ECU, nâng cấp hệ thống ống xả Titanium, phanh Carbon
              Ceramic chính hãng từ Akrapovič, Brembo.
            </p>
          </div>

          <div className="p-8 border border-neutral-800 bg-neutral-950/80 relative overflow-hidden group hover:border-red-900/50 transition duration-500">
            <div className="absolute top-0 right-0 w-24 h-24 bg-red-600/5 blur-2xl group-hover:bg-red-600/20 transition" />
            <Shield size={36} className="text-blue-500 mb-6" />
            <h3 className="text-xl font-extrabold uppercase tracking-wide text-white mb-2">
              Bảo Hảnh Độc Quyền
            </h3>
            <p className="text-neutral-400 text-sm leading-relaxed">
              Gói bảo hành hiệu năng cao 24 tháng cho cả động cơ và hộp số. Hỗ
              trợ cứu hộ chuyên dụng 24/7 toàn quốc.
            </p>
          </div>
        </div>
      </section>

      {/* 3. SHOWROOM SẢN PHẨM (XE = LIÊN HỆ BÁO GIÁ) */}
      <section className="py-24 max-w-7xl mx-auto px-6">
        <div className="flex flex-col md:flex-row justify-between items-start md:items-end mb-12 border-b border-neutral-800 pb-6">
          <div>
            <span className="text-xs font-mono uppercase tracking-widest text-red-500 font-bold">
              Featured Fleet
            </span>
            <h2 className="text-3xl sm:text-4xl font-black uppercase tracking-tight text-white mt-1">
              Siêu Xe Đang Có Mặt Tại Showroom
            </h2>
          </div>
          <Link
            to="/vehicles"
            className="text-xs font-mono uppercase tracking-widest text-neutral-400 hover:text-red-500 transition-colors mt-4 md:mt-0 flex items-center gap-1"
          >
            Xem toàn bộ kho xe ({">"}50) <ArrowUpRight size={14} />
          </Link>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {featuredCars.map((car) => (
            <div
              key={car.id}
              className="bg-neutral-900/60 border border-neutral-800 hover:border-neutral-700 transition duration-300 flex flex-col group overflow-hidden"
            >
              {/* Ảnh Xe */}
              <div className="relative aspect-[16/10] overflow-hidden bg-neutral-950">
                <img
                  src={car.image}
                  alt={car.name}
                  className="w-full h-full object-cover group-hover:scale-105 transition duration-700 opacity-90 group-hover:opacity-100"
                />
                <div className="absolute top-3 left-3 bg-neutral-950/80 backdrop-blur px-2.5 py-1 text-[10px] font-mono uppercase tracking-wider text-neutral-300 border border-neutral-800">
                  {car.type}
                </div>
              </div>

              {/* Thông tin & Thông số kỹ thuật */}
              <div className="p-6 flex-1 flex flex-col justify-between space-y-6">
                <div>
                  <h3 className="text-xl font-bold uppercase tracking-tight text-white mb-2 group-hover:text-red-500 transition-colors">
                    {car.name}
                  </h3>
                  <p className="text-2xl font-black text-amber-500 font-mono">
                    {car.price}
                  </p>
                </div>

                {/* Performance Badge Specs */}
                <div className="grid grid-cols-3 gap-2 py-3 border-y border-neutral-800 text-center font-mono text-xs">
                  <div className="bg-neutral-950 p-2">
                    <span className="block text-neutral-500 text-[10px] uppercase">
                      Công suất
                    </span>
                    <span className="font-bold text-white">{car.hp}</span>
                  </div>
                  <div className="bg-neutral-950 p-2">
                    <span className="block text-neutral-500 text-[10px] uppercase">
                      Gia tốc
                    </span>
                    <span className="font-bold text-red-500">{car.speed}</span>
                  </div>
                  <div className="bg-neutral-950 p-2">
                    <span className="block text-neutral-500 text-[10px] uppercase">
                      Động cơ
                    </span>
                    <span className="font-bold text-neutral-300 truncate block">
                      {car.engine}
                    </span>
                  </div>
                </div>

                {/* NÚT LIÊN HỆ CHI TIẾT (KHÔNG CÓ GIỎ HÀNG) */}
                <div className="grid grid-cols-2 gap-3 pt-2">
                  <Link
                    to={`/vehicles/${car.id}`}
                    className="w-full py-3 bg-neutral-800 hover:bg-neutral-700 text-white font-bold text-xs uppercase tracking-wider text-center transition"
                  >
                    Chi Tiết
                  </Link>

                  <a
                    href="https://zalo.me"
                    target="_blank"
                    rel="noreferrer"
                    className="w-full py-3 bg-red-600 hover:bg-red-700 text-white font-extrabold text-xs uppercase tracking-wider text-center transition flex items-center justify-center gap-1.5"
                  >
                    <MessageSquare size={14} /> Liên Hệ VIP
                  </a>
                </div>
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}
