import { createContext, useState, useEffect } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [customer, setCustomer] = useState(null);

  useEffect(() => {
    const storedCustomer = localStorage.getItem("customer");
    if (storedCustomer) {
      setCustomer(JSON.parse(storedCustomer));
    }
  }, []);

  return (
    <AuthContext.Provider value={{ customer, setCustomer }}>
      {children}
    </AuthContext.Provider>
  );
};
