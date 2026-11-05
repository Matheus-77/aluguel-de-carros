import { CarFront } from 'lucide-react'

export default function MarcaProjeto() {
  return (
    <span className="marca-projeto">
      <span className="marca-simbolo"><CarFront aria-hidden="true" /></span>
      <span className="marca-nome">Rota<span className="marca-destaque">Certa</span></span>
    </span>
  )
}