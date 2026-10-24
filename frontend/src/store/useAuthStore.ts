import { create } from "zustand";
import { persist } from "zustand/middleware";

interface User {
  email: string;
  fullName: string;
  role: string;
}

interface AuthState {
  user: User | null;
  token: string | null;
  login: (userData: User, token: string) => void;
  logout: () => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      token: null,

      login: (userData, token) => {
        set({ user: userData, token });
      },

      logout: () => {
        set({ user: null, token: null });
        localStorage.removeItem("accessToken");
      },
    }),
    {
      name: "auth-storage",
    },
  ),
);
