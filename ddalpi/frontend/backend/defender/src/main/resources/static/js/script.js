document.addEventListener('DOMContentLoaded', () => {
    const defenseStatusElement = document.getElementById('defense-status');
    const mechanismDescriptionElement = document.querySelector('.mechanism-description p');
    const mechanismTitleElement = document.querySelector('.mechanism-description h4'); // h4 요소 선택

    // 방어법 버튼 클릭 이벤트 설정
    document.getElementById('defense-btn-1').addEventListener('click', () => {
        defenseStatusElement.textContent = '특정 IP SLEEP 방어법이 실행 중입니다.';
        mechanismTitleElement.textContent = '특정 IP SLEEP'; // h4 내용 변경
        mechanismDescriptionElement.innerHTML =
            '특정 IP의 요청 횟수에 따라 CPU Sleep 시간을 동적으로 늘려 과도한 요청을 억제하는 방어법입니다.<br>' +
            '서버에 들어오는 각 IP의 단위 시간당 요청을 기록하고, 요청 횟수가 많아질수록 Sleep 시간을 증가시켜 시스템 자원을 보호합니다.<br>' +
            '미들웨어에서 스레드마다 개별적으로 Sleep 시간을 적용하여 공격자의 트래픽을 지연시키는 방식입니다.<br>' +
            '이를 통해 공격자가 공격을 중단하도록 유도하고, 정상 사용자의 서비스 품질을 유지할 수 있습니다.<br>' +
            '결과적으로 시스템의 안정성을 높이고 자원을 효율적으로 활용할 수 있습니다.';
    });

    document.getElementById('defense-btn-2').addEventListener('click', () => {
        defenseStatusElement.textContent = 'VM 방화벽 설정을 통한 패킷 드랍 방어법이 실행 중입니다.';
        mechanismTitleElement.textContent = 'VM 방화벽 설정을 통한 패킷 드랍'; // h4 내용 변경
        mechanismDescriptionElement.innerHTML =
            'VM 방화벽 설정을 통해 단위 시간당 요청 횟수가 많은 비정상 사용자를 차단하는 방어법입니다.<br>' +
            '요청 횟수가 많을수록 해당 사용자가 공격자로 의심될 확률이 증가하고, 일정 확률로 IP를 방화벽에 전달하여 iptables로 네트워크 패킷을 드랍합니다.<br>' +
            '이를 통해 비정상 사용자의 공격 효과를 줄이고, 정상 사용자는 방화벽 규칙에 거의 영향을 받지 않아 서비스가 원활하게 유지됩니다.<br>' +
            '서버로 불필요한 요청이 전달되지 않아 자원 소모를 줄이고, 공격자의 응답 시간을 지연시켜 공격 효과를 감소시킵니다.';
    });

    document.getElementById('defense-btn-3').addEventListener('click', () => {
        defenseStatusElement.textContent = '더미 서버 활용 로드 밸런싱 방어법이 실행 중입니다.';
        mechanismTitleElement.textContent = '더미 서버 활용 로드 밸런싱'; // h4 내용 변경
        mechanismDescriptionElement.innerHTML =
            '더미 서버를 활용한 로드 밸런싱은 비정상 사용자의 요청을 지연시키기 위한 방어법입니다.<br>' +
            '정상 서버와 동일한 로직의 더미 서버를 준비하고, 해당 서버는 고의적으로 응답을 지연시킵니다.<br>' +
            '단위 시간당 요청 횟수를 기반으로 공격자로 의심되는 IP를 일정 확률로 더미 서버로 리다이렉션합니다.<br>' +
            '정상 사용자는 더미 서버로 갈 확률이 낮고, 만약 더미 서버로 연결되더라도 재요청 시 정상 서버로 연결될 가능성이 큽니다.<br>' +
            '이 방법은 공격자의 응답 시간을 교란하면서 정상 사용자의 피해를 최소화할 수 있습니다.';
    });
});