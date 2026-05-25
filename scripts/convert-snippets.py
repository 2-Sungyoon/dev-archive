#!/usr/bin/env python3
"""
Spring REST Docs snippets → Markdown 변환 스크립트

사용법: python3 convert-snippets.py <snippets-dir> <output-dir>

snippets 디렉토리 구조:
  build/generated-snippets/
    auth/login-success/
      request-fields.adoc
      response-fields.adoc
      curl-request.adoc
      http-request.adoc
      http-response.adoc

출력:
  docs/api/auth/login-success.md
"""

import os
import sys
from pathlib import Path


def read_snippet(snippet_dir: Path, name: str) -> str:
    """snippet 파일을 읽어 반환. 없으면 빈 문자열."""
    file_path = snippet_dir / f"{name}.adoc"
    if file_path.exists():
        return file_path.read_text(encoding="utf-8")
    return ""


def adoc_table_to_md(adoc_content: str) -> str:
    """AsciiDoc 테이블을 Markdown 테이블로 변환."""
    if not adoc_content.strip():
        return "_없음_\n"

    lines = adoc_content.strip().split("\n")
    md_lines = []
    in_table = False
    header_done = False

    for line in lines:
        # AsciiDoc 테이블 구분자 무시
        if line.startswith("|==="):
            in_table = not in_table
            continue
        if line.startswith("|"):
            cells = [c.strip() for c in line.split("|")[1:] if c.strip()]
            if cells:
                md_line = "| " + " | ".join(cells) + " |"
                md_lines.append(md_line)
                if not header_done:
                    md_lines.append("| " + " | ".join(["---"] * len(cells)) + " |")
                    header_done = True

    return "\n".join(md_lines) + "\n" if md_lines else adoc_content


def convert_snippet_group(snippet_dir: Path) -> str:
    """하나의 snippet 그룹을 마크다운 문서로 변환."""
    name = snippet_dir.name
    title = name.replace("-", " ").title()

    md = f"# {title}\n\n"

    # HTTP Request
    http_request = read_snippet(snippet_dir, "http-request")
    if http_request:
        md += "## HTTP Request\n\n```http\n"
        # AsciiDoc 코드블록 마커 제거
        content = http_request.replace("[source,http,options=\"nowrap\"]", "").strip()
        content = "\n".join(
            line for line in content.split("\n")
            if not line.startswith("----") and not line.startswith("..")
        )
        md += content.strip() + "\n```\n\n"

    # Request Fields
    request_fields = read_snippet(snippet_dir, "request-fields")
    if request_fields:
        md += "## Request Fields\n\n"
        md += adoc_table_to_md(request_fields)
        md += "\n"

    # Request Headers
    request_headers = read_snippet(snippet_dir, "request-headers")
    if request_headers:
        md += "## Request Headers\n\n"
        md += adoc_table_to_md(request_headers)
        md += "\n"

    # HTTP Response
    http_response = read_snippet(snippet_dir, "http-response")
    if http_response:
        md += "## HTTP Response\n\n```http\n"
        content = http_response.replace("[source,http,options=\"nowrap\"]", "").strip()
        content = "\n".join(
            line for line in content.split("\n")
            if not line.startswith("----") and not line.startswith("..")
        )
        md += content.strip() + "\n```\n\n"

    # Response Fields
    response_fields = read_snippet(snippet_dir, "response-fields")
    if response_fields:
        md += "## Response Fields\n\n"
        md += adoc_table_to_md(response_fields)
        md += "\n"

    # cURL
    curl_request = read_snippet(snippet_dir, "curl-request")
    if curl_request:
        md += "## cURL Example\n\n```bash\n"
        content = curl_request.replace("[source,bash]", "").strip()
        content = "\n".join(
            line for line in content.split("\n")
            if not line.startswith("----") and not line.startswith("..")
        )
        md += content.strip() + "\n```\n\n"

    return md


def main():
    if len(sys.argv) != 3:
        print(f"Usage: {sys.argv[0]} <snippets-dir> <output-dir>")
        sys.exit(1)

    snippets_dir = Path(sys.argv[1])
    output_dir = Path(sys.argv[2])

    if not snippets_dir.exists():
        print(f"Error: snippets directory not found: {snippets_dir}")
        sys.exit(1)

    # snippets 디렉토리 탐색
    for domain_dir in sorted(snippets_dir.iterdir()):
        if not domain_dir.is_dir():
            continue

        domain_name = domain_dir.name  # e.g., "auth", "member"

        for snippet_group in sorted(domain_dir.iterdir()):
            if not snippet_group.is_dir():
                continue

            # 변환
            markdown = convert_snippet_group(snippet_group)

            # 출력 경로 생성
            output_path = output_dir / domain_name / f"{snippet_group.name}.md"
            output_path.parent.mkdir(parents=True, exist_ok=True)
            output_path.write_text(markdown, encoding="utf-8")
            print(f"Generated: {output_path}")

    # 각 도메인별 index.md 생성
    for domain_dir in sorted(output_dir.iterdir()):
        if not domain_dir.is_dir():
            continue
        if domain_dir.name.startswith("."):
            continue

        index_content = f"# {domain_dir.name.title()} API\n\n"
        for md_file in sorted(domain_dir.glob("*.md")):
            if md_file.name == "index.md":
                continue
            title = md_file.stem.replace("-", " ").title()
            index_content += f"- [{title}](./{md_file.name})\n"

        index_path = domain_dir / "index.md"
        index_path.write_text(index_content, encoding="utf-8")
        print(f"Generated: {index_path}")

    print("\nDone! All snippets converted to markdown.")


if __name__ == "__main__":
    main()
