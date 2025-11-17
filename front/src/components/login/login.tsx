import React from 'react'

const Login = () => {
  return (
    <div className="flex flex-col items-center justify-center h-screen bg-background text-foreground">
      <h1 className="text-3xl font-bold text-primary">Login</h1>
      <p className="text-muted-foreground mt-2">Iniciá sesión para continuar</p>
      <button className="mt-6 px-4 py-2 rounded-md bg-primary text-primary-foreground hover:bg-primary/90 transition">
        Ingresar
      </button>
    </div>
  );
}

export default Login