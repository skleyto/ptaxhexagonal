import React, { useState } from "react";

function ConsultaPtax() {
  const [data, setData] = useState("");
  const [cotacaoCompra, setCotacaoCompra] = useState("");
  const [cotacaoVenda, setCotacaoVenda] = useState("");
  const [dataHoraCotacao, setDataHoraCotacao] = useState("");
  const [erro, setErro] = useState("");

  const consultar = async () => {
    setErro("");
    setCotacaoCompra("");
    setCotacaoVenda("");
    setDataHoraCotacao("");
    try {
      const resp = await fetch(`http://localhost:7000/ptax/${data}`);
      if (resp.ok) {
        const json = await resp.json();
        setCotacaoCompra(json.cotacaoCompra);
        setCotacaoVenda(json.cotacaoVenda);
        setDataHoraCotacao(json.dataHoraCotacao);
      } else {
        const json = await resp.json();
        setErro(json.erro || "Erro ao consultar API");
      }
    } catch (e) {
      setErro("Erro de conexão com a API");
    }
  };

  return (
    <div style={{
      minHeight: "100vh",
      background: "linear-gradient(135deg, #e0eafc 0%, #cfdef3 100%)",
      display: "flex",
      alignItems: "center",
      justifyContent: "center"
    }}>
      <div style={{
        background: "#fff",
        borderRadius: 16,
        boxShadow: "0 4px 24px rgba(0,0,0,0.10)",
        padding: 32,
        width: 370,
        maxWidth: "90vw"
      }}>
        <h2 style={{
          textAlign: "center",
          color: "#2b5876",
          marginBottom: 24,
          letterSpacing: 1
        }}>
          Consulta PTAX Bacen
        </h2>
        <label style={{ fontWeight: 500, color: "#333" }}>
          Data (aaaa-mm-dd):
          <input
            type="date"
            value={data}
            onChange={(e) => setData(e.target.value)}
            style={{
              marginLeft: 10,
              padding: "6px 10px",
              borderRadius: 6,
              border: "1px solid #b0b0b0",
              fontSize: 16,
              outline: "none"
            }}
          />
        </label>
        <button
          onClick={consultar}
          style={{
            marginTop: 24,
            width: "100%",
            padding: "10px 0",
            background: "linear-gradient(90deg, #36d1c4 0%, #5b86e5 100%)",
            color: "#fff",
            border: "none",
            borderRadius: 8,
            fontWeight: 600,
            fontSize: 18,
            cursor: "pointer",
            boxShadow: "0 2px 8px rgba(91,134,229,0.10)"
          }}
        >
          Consultar
        </button>
        {cotacaoCompra && (
          <div style={{
            marginTop: 32,
            background: "#f6fafd",
            borderRadius: 10,
            padding: 20,
            boxShadow: "0 2px 8px rgba(44, 62, 80, 0.06)",
            border: "1px solid #e3eafc"
          }}>
            <p style={{ fontSize: 17, margin: "10px 0" }}>
              <span style={{ color: "#2b5876", fontWeight: 600 }}>Cotação de Compra:</span>
              <span style={{ marginLeft: 8, color: "#222" }}>{cotacaoCompra}</span>
            </p>
            <p style={{ fontSize: 17, margin: "10px 0" }}>
              <span style={{ color: "#2b5876", fontWeight: 600 }}>Cotação de Venda:</span>
              <span style={{ marginLeft: 8, color: "#222" }}>{cotacaoVenda}</span>
            </p>
            <p style={{ fontSize: 16, margin: "10px 0" }}>
              <span style={{ color: "#5b86e5", fontWeight: 500 }}>Data/Hora Cotação:</span>
              <span style={{ marginLeft: 8, color: "#444" }}>{dataHoraCotacao}</span>
            </p>
          </div>
        )}
        {erro && (
          <div style={{
            marginTop: 24,
            color: "#fff",
            background: "#e74c3c",
            borderRadius: 8,
            padding: "10px 16px",
            textAlign: "center",
            fontWeight: 500
          }}>
            {erro}
          </div>
        )}
      </div>
    </div>
  );
}

export default ConsultaPtax;