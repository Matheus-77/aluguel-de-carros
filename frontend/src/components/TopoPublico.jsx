import BotaoTema from './BotaoTema'
import MarcaProjeto from './MarcaProjeto'

export default function TopoPublico({ tela, onTrocarTela }) {
  return (
    <header className="topo-publico">
      <div className="topo-publico-interno">
        <button type="button" className="logo-botao" onClick={() => onTrocarTela('inicio')} aria-label="RotaCerta, ir para o início">
          <MarcaProjeto />
        </button>
        {tela === 'inicio' && (
          <nav className="topo-links" aria-label="Seções">
            <a href="#recursos">Recursos</a>
            <a href="#como-funciona">Como funciona</a>
            <a href="#para-quem">Para quem</a>
          </nav>
        )}
        <div className="topo-publico-acoes">
          <BotaoTema />
          <button type="button" className="contorno" onClick={() => onTrocarTela('login')}>Entrar</button>
          <button type="button" onClick={() => onTrocarTela('cadastro')}>Cadastrar</button>
        </div>
      </div>
    </header>
  )
}
