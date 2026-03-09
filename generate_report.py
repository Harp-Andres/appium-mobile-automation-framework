#!/usr/bin/env python3
import json
import os
from datetime import datetime
from pathlib import Path

# Rutas
project_root = Path(__file__).parent
allure_results_dir = project_root / "target" / "allure-results"
report_dir = project_root / "target" / "allure-report-html"
report_dir.mkdir(parents=True, exist_ok=True)

# Leer archivos de resultados de Allure
results = []
if allure_results_dir.exists():
    for json_file in allure_results_dir.glob("*-result.json"):
        with open(json_file, 'r', encoding='utf-8') as f:
            try:
                result = json.load(f)
                results.append(result)
            except:
                pass

# Contar resultados
passed = sum(1 for r in results if r.get('status') == 'passed')
failed = sum(1 for r in results if r.get('status') == 'failed')
skipped = sum(1 for r in results if r.get('status') == 'skipped')
total = len(results)

# Generar HTML
html_content = f"""<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reporte de Allure - {datetime.now().strftime('%Y-%m-%d %H:%M')}</title>
    <style>
        * {{ margin: 0; padding: 0; box-sizing: border-box; }}
        body {{
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }}
        .container {{ max-width: 1200px; margin: 0 auto; }}
        .header {{
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }}
        .header h1 {{ color: #333; margin-bottom: 10px; font-size: 2.5em; }}
        .header p {{ color: #666; font-size: 1.1em; }}
        .stats {{
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }}
        .stat {{
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 10px;
            text-align: center;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }}
        .stat.passed {{ background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); }}
        .stat.failed {{ background: linear-gradient(135deg, #eb3349 0%, #f45c43 100%); }}
        .stat.skipped {{ background: linear-gradient(135deg, #fa709a 0%, #fee140 100%); }}
        .stat h3 {{ font-size: 2.5em; margin-bottom: 10px; }}
        .stat p {{ font-size: 1em; opacity: 0.9; }}
        .results {{
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }}
        .results h2 {{ margin-bottom: 20px; color: #333; }}
        .test-item {{
            padding: 15px;
            border-left: 4px solid #ccc;
            margin-bottom: 10px;
            border-radius: 5px;
            background: #f9f9f9;
        }}
        .test-item.passed {{ border-left-color: #38ef7d; }}
        .test-item.failed {{ border-left-color: #f45c43; }}
        .test-item.skipped {{ border-left-color: #fee140; }}
        .test-name {{ font-weight: bold; color: #333; margin-bottom: 5px; }}
        .test-time {{ font-size: 0.9em; color: #666; }}
        .footer {{
            text-align: center;
            color: white;
            margin-top: 30px;
            padding: 20px;
        }}
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📊 Reporte de Pruebas Allure</h1>
            <p>Generado: {datetime.now().strftime('%d/%m/%Y %H:%M:%S')}</p>
            <div class="stats">
                <div class="stat passed">
                    <h3>{passed}</h3>
                    <p>Exitosas</p>
                </div>
                <div class="stat failed">
                    <h3>{failed}</h3>
                    <p>Fallidas</p>
                </div>
                <div class="stat skipped">
                    <h3>{skipped}</h3>
                    <p>Omitidas</p>
                </div>
                <div class="stat">
                    <h3>{total}</h3>
                    <p>Total</p>
                </div>
            </div>
        </div>

        <div class="results">
            <h2>📋 Detalle de Pruebas</h2>
            {generate_test_items(results)}
        </div>

        <div class="footer">
            <p>💻 Appium + Cucumber + JUnit5 + Allure Framework</p>
            <p>✨ Automatización Moderna de Pruebas</p>
        </div>
    </div>
</body>
</html>
"""

def generate_test_items(results):
    if not results:
        return "<p style='color: #999;'>No hay resultados de pruebas registrados.</p>"

    items = ""
    for result in results:
        name = result.get('name', 'Sin nombre')
        status = result.get('status', 'unknown')
        duration = result.get('stop', 0) - result.get('start', 0)
        duration_s = f"{duration/1000:.2f}s" if duration > 0 else "0s"

        items += f"""<div class="test-item {status}">
            <div class="test-name">✓ {name}</div>
            <div class="test-time">Estado: {status.upper()} | Duración: {duration_s}</div>
        </div>
"""
    return items

# Escribir HTML
report_file = report_dir / "index.html"
with open(report_file, 'w', encoding='utf-8') as f:
    f.write(html_content)

print(f"✅ Reporte generado: {report_file}")
print(f"📊 Resultados: {passed} exitosas, {failed} fallidas, {skipped} omitidas")

