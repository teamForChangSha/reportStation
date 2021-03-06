<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=7,IE=8,IE=edge">
    <meta http-equiv="cache-control" content="no-cache">
    <title></title>
</head>

<body>
<h4>隐私声明</h4>
<c:if test="${companyBranch.owner.otherInfo.spHtml!=null}">
    ${companyBranch.owner.otherInfo.spHtml}
</c:if>
<c:if test="${companyBranch.owner.otherInfo.spHtml==null}">
    <c:if test="${companyBranch.owner.otherInfo.serviceProtocol==null}">
        <p>${companyBranch.owner.companyName} 诚信帮助热线是一个由 ${companyBranch.owner.companyName}
            提供的系统，让您能够申请获得指导或合规培训，或者举报银行或金融犯罪、内幕交易、诈骗、贿赂或其他腐败行为，以及与公司的财务、会计或审计规范相关的问题。使用 ${companyBranch.owner.companyName}
            诚信帮助热线，不管是通过在线方式还是通过电话方式，都是完全自愿的。如果您的报告与其他道德或合规问题有关，我们鼓励您直接通过其他已有的报告渠道提出，包括向您的管理层、EMEA 地区合规部门以及道德主管报告。</p>

        <p><strong>我们会收集哪些信息？</strong>我们将要求您提交与您的申请或报告相关的信息。${companyBranch.owner.companyName} 不会使用任何手段（例如放置 Cookie
            或收集电子通信协议，以及使用来电跟踪或录音）采集与您使用 ${companyBranch.owner.companyName} 诚信帮助热线相关的信息。</p>

        <p>请留意您提供的有关您自己、您的同事或者 ${companyBranch.owner.companyName}
            运营中的任何方面的信息，它们可能会形成对他人造成影响的决策。因此，我们要求您尽可能只提供正确的信息。对于您出于善意提交的信息，即使后来发现是不正确的，您也不会受到处罚。同时，蓄意提供虚假或误导信息是不可容忍的，且可能导致纪律处分。公司将对您提交的信息进行慎重处理。我们鼓励您表明自己的身份，以便我们就任何可能遇到的问题与您进行后续交流。</p>

        <p><strong>信息将如何使用？</strong>您提供的所有信息都将存放在 51Report托管的 ${companyBranch.owner.companyName}
            诚信帮助热线服务器上，服务器位于美国。51Report
            遵守安全港原则。除非法律要求，否则 51Report 服务器上的信息只能供因工作职责需要了解相关信息的授权人士查看并使用。这些人士可能包括 ${companyBranch.owner.companyName}
            法律部、人力资源部、商业评估及审计部、财务部、全球信息安全部和/或管理层的成员、合规及道德组织中的成员以及外部人员，例如外部顾问、审计师、取证服务提供商和/或
            51Report 的技术员工。这些人士可能位于美国或其他国家/地区，不能提供与欧盟法律相同级别的数据保护。此外，您提供的所有信息都可能被上述人士另外存储。</p>

        <p>${companyBranch.owner.companyName}
            将评估您提供的信息、回答您询问的问题、进行任何恰当的调查，并采取必要的纠正措施。请注意，您通过 ${companyBranch.owner.companyName}
            诚信帮助热线指认的所有人士都可能获悉自己被举报。此外，您指认的所有人士都有权对您报告的信息进行回应或纠正。</p>

        <p>当我们对您的问询进行回复或完成任何必要的调查后，您提交的所有信息都将根据需要以及当地法律的要求进行删除或存档。${companyBranch.owner.companyName}
            将采取充分的技术、组织和法律措施来保护您提供的信息。${companyBranch.owner.companyName} 还要求 51Report 充分保护您的个人数据，并且不得将其用于任何未授权的目的。</p>
    </c:if>
    <p>${companyBranch.owner.otherInfo.serviceProtocol}</p>
</c:if>

</body>

</html>